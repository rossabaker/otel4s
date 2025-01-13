/*
 * Copyright 2023 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.typelevel.otel4s.sdk.autoconfigure

import cats.Functor
import cats.effect.Sync
import cats.effect.std.Env
import cats.syntax.either._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._

import scala.concurrent.duration._

/** The config to use for auto-configuration of the SDK components.
  */
sealed trait Config {

  /** Returns the decoded value for the given key.
    *
    * @param key
    *   the key a value is associated with
    *
    * @tparam A
    *   the type of a value
    *
    * @return
    *   - `Left(ConfigurationError)` - when the key exists in the config but cannot be decoded as `A`
    *   - `Right(None)` - when the key does not exist in the config
    *   - `Right(Some(a))` - when the key exists in the config and successfully decoded as `A`
    */
  def get[A: Config.Reader](key: String): Either[ConfigurationError, Option[A]]

  /** Returns the decoded value for the given key or the default one.
    *
    * @param key
    *   the key a value is associated with
    *
    * @param default
    *   the default value
    *
    * @tparam A
    *   the type of a value
    */
  final def getOrElse[A: Config.Reader](
      key: String,
      default: => A
  ): Either[ConfigurationError, A] =
    get[A](key).map(_.getOrElse(default))

  /** Returns the decoded value for the given key.
    *
    * @param key
    *   the key a value is associated with
    *
    * @tparam A
    *   the type of a value
    *
    * @return
    *   - `Left(ConfigurationError)` - when the key exists in the config but cannot be decoded as `A`
    *   - `Right(None)` - when the key does not exist in the config
    *   - `Right(Some(a))` - when the key exists in the config and successfully decoded as `A`
    */
  final def get[A: Config.Reader](
      key: Config.Key[A]
  ): Either[ConfigurationError, Option[A]] =
    get[A](key.name)

  /** Returns the decoded value for the given key or the default one.
    *
    * @param key
    *   the key a value is associated with
    *
    * @param default
    *   the default value
    *
    * @tparam A
    *   the type of a value
    */
  final def getOrElse[A: Config.Reader](
      key: Config.Key[A],
      default: => A
  ): Either[ConfigurationError, A] =
    get[A](key).map(_.getOrElse(default))

  /** Returns a copy of the config with the overriden values.
    *
    * @param overrides
    *   the values to override
    */
  def withOverrides(overrides: Map[String, String]): Config
}

object Config {

  final class Key[A] private (val name: String) {
    override def toString: String = name
  }
  object Key {
    def apply[A](name: String): Key[A] = new Key[A](name)
  }

  /** Decodes a string value as `A`.
    *
    * @tparam A
    *   the type of a value
    */
  trait Reader[A] {
    def read(
        key: String,
        properties: Map[String, String]
    ): Either[ConfigurationError, Option[A]]
  }

  object Reader {
    def apply[A](implicit ev: Reader[A]): Reader[A] = ev

    implicit val stringReader: Reader[String] =
      (key, props) => Right(props.get(key).map(_.trim).filter(_.nonEmpty))

    implicit val booleanReader: Reader[Boolean] =
      decodeWithHint("Boolean")(v => Right(v.toBoolean))

    implicit val doubleReader: Reader[Double] =
      decodeWithHint("Double")(v => Right(v.toDouble))

    implicit val intReader: Reader[Int] =
      decodeWithHint("Int")(v => Right(v.toInt))

    implicit val finiteDurationReader: Reader[FiniteDuration] =
      decodeWithHint("FiniteDuration") { string =>
        string.toIntOption match {
          case Some(number) =>
            Right(number.millis)

          case None =>
            Duration(string) match {
              case duration: FiniteDuration =>
                Right(duration)
              case _ =>
                Left(ConfigurationError("The duration must be finite"))
            }
        }
      }

    implicit val stringListReader: Reader[List[String]] =
      decodeWithHint("List[String]")(string => Right(asStringList(string)))

    implicit val stringSetReader: Reader[Set[String]] =
      decodeWithHint("Set[String]") { string =>
        val list = asStringList(string)
        val set = list.toSet

        Either.cond(
          test = set.size == list.size,
          right = set,
          left = {
            val duplicates = list.diff(set.toSeq).toSet.mkString("[", ", ", "]")
            ConfigurationError(
              s"The string set contains duplicates: $duplicates"
            )
          }
        )
      }

    implicit val stringMapReader: Reader[Map[String, String]] =
      decodeWithHint("Map[String, String]") { string =>
        val list = asStringList(string)

        list
          .traverse { entry =>
            val parts = entry.split("=", 2).map(_.trim)
            Either.cond(
              test = parts.length == 2 && parts.head.nonEmpty,
              right = parts.toList,
              left = ConfigurationError(s"Invalid map property [$entry]")
            )
          }
          .map { entries =>
            entries
              .collect { case key :: value :: Nil => (key, value) }
              .groupMapReduce(_._1)(_._2)((_, last) => last)
          }
      }

    implicit val readerFunctor: Functor[Reader] =
      new Functor[Reader] {
        def map[A, B](fa: Reader[A])(f: A => B): Reader[B] =
          (key, properties) => fa.read(key, properties).map(_.map(f))
      }

    private def asStringList(string: String): List[String] =
      string.split(",").map(_.trim).filter(_.nonEmpty).toList

    private[otel4s] def decodeWithHint[A](
        hint: String
    )(decode: String => Either[ConfigurationError, A]): Reader[A] =
      (key, properties) =>
        Reader[String].read(key, properties).flatMap {
          case Some(value) =>
            Either
              .catchNonFatal(decode(value))
              .leftMap { cause =>
                ConfigurationError(
                  s"Invalid value for property $key=$value. Must be [$hint]",
                  cause
                )
              }
              .flatten
              .map(Some(_))

          case None => Right(None)
        }

  }

  /** Creates a [[Config]] with the given properties. The keys of the properties will be normalized.
    *
    * The priorities of the values: system properties > env variables > default.
    *
    * @param systemProperties
    *   the system properties (e.g. `sys.props`)
    *
    * @param environmentVariables
    *   the env variables (e.g. `sys.env`)
    *
    * @param defaultProperties
    *   the default properties
    */
  def apply(
      systemProperties: Map[String, String],
      environmentVariables: Map[String, String],
      defaultProperties: Map[String, String]
  ): Config = {
    val default = defaultProperties.map { case (key, value) =>
      (normalizePropertyKey(key), value)
    }

    val env = environmentVariables.map { case (key, value) =>
      (normalizeEnvVariableKey(key), value)
    }

    val props = systemProperties.map { case (key, value) =>
      (normalizePropertyKey(key), value)
    }

    Impl(default ++ env ++ props)
  }

  /** Creates a [[Config]] with the given properties. The properties will be treated as the system props.
    *
    * @param properties
    *   the properties to use
    */
  def ofProps(properties: Map[String, String]): Config =
    apply(properties, Map.empty, Map.empty)

  /** Creates a [[Config]] by loading properties from the env variables and system props.
    *
    * @param default
    *   the default properties
    */
  def load[F[_]: Sync: Env](default: Map[String, String]): F[Config] =
    for {
      envVars <- Env[F].entries
      systemProps <- Sync[F].delay(sys.props.toMap)
    } yield apply(systemProps, envVars.toMap, default)

  private final case class Impl[F[_]](
      properties: Map[String, String]
  ) extends Config {

    def get[A: Reader](key: String): Either[ConfigurationError, Option[A]] =
      Reader[A].read(normalizePropertyKey(key), properties)

    def withOverrides(overrides: Map[String, String]): Config = {
      val normalized = overrides.map { case (key, value) =>
        (normalizePropertyKey(key), value)
      }

      copy(properties = this.properties ++ normalized)
    }

  }

  /** Normalizes a property key by converting to lower case and replacing "-" with ".".
    */
  private def normalizePropertyKey(key: String): String =
    key.toLowerCase.replace("-", ".");

  /** Normalizes an env variable key by converting to lower case and replacing "_" with ".".
    */
  private def normalizeEnvVariableKey(key: String): String =
    key.toLowerCase.replace("_", ".")

}
