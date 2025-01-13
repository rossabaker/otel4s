/*
 * Copyright 2022 Typelevel
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

/*
 * The scaladocs in this file are adapted from those in the file
 *   scala/jdk/CollectionConverters.scala
 * in the Scala standard library.
 */

package org.typelevel.otel4s.oteljava

import io.opentelemetry.api.common.{AttributeKey => JAttributeKey}
import io.opentelemetry.api.common.{AttributeType => JAttributeType}
import io.opentelemetry.api.common.{Attributes => JAttributes}
import org.typelevel.otel4s.Attribute
import org.typelevel.otel4s.AttributeKey
import org.typelevel.otel4s.AttributeType
import org.typelevel.otel4s.Attributes

import java.{util => ju}
import scala.collection.immutable
import scala.jdk.CollectionConverters._

/** This object provides extension methods that convert between Scala and Java `AttributeKey` s and `Attributes` using
  * `toScala` and `toJava` extension methods, as well as a `toJavaAttributes` extension method that converts
  * `immutable.Iterable[Attribute[_]]` to Java `Attributes`.
  *
  * {{{
  *   import io.opentelemetry.api.common.{Attributes => JAttributes}
  *   import org.typelevel.otel4s.Attributes
  *   import org.typelevel.otel4s.oteljava.AttributeConverters._
  *
  *   val attributes: Attributes =
  *     io.opentelemetry.api.common.Attributes.builder()
  *       .put("key", "value")
  *       .build()
  *       .toScala
  * }}}
  *
  * The conversions do not return wrappers.
  */
object AttributeConverters {

  implicit final class AttributeKeyHasToJava(private val key: AttributeKey[_]) extends AnyVal {

    /** Converts a Scala `AttributeKey` to a Java `AttributeKey`. */
    def toJava: JAttributeKey[_] = Explicit.toJava(key)
  }

  implicit final class AttributesHasToJava(private val attributes: Attributes) extends AnyVal {

    /** Converts Scala `Attributes` to Java `Attributes`. */
    def toJava: JAttributes = Explicit.toJava(attributes)
  }

  implicit final class IterableAttributeHasToJava(
      private val attributes: immutable.Iterable[Attribute[_]]
  ) extends AnyVal {

    /** Converts an immutable collection of Scala `Attribute` s to Java `Attributes`.
      */
    def toJavaAttributes: JAttributes = Explicit.toJavaAttributes(attributes)
  }

  implicit final class AttributeKeyHasToScala(private val key: JAttributeKey[_]) extends AnyVal {

    /** Converts a Java `AttributeKey` to a Scala `AttributeKey`. */
    def toScala: AttributeKey[_] = Explicit.toScala(key)
  }

  implicit final class AttributesHasToScala(private val attributes: JAttributes) extends AnyVal {

    /** Converts Scala `Attributes` to Java `Attributes`. */
    def toScala: Attributes = Explicit.toScala(attributes)
  }

  private object Explicit {
    def toJava(key: AttributeKey[_]): JAttributeKey[_] =
      key.`type` match {
        case AttributeType.String     => JAttributeKey.stringKey(key.name)
        case AttributeType.Boolean    => JAttributeKey.booleanKey(key.name)
        case AttributeType.Long       => JAttributeKey.longKey(key.name)
        case AttributeType.Double     => JAttributeKey.doubleKey(key.name)
        case AttributeType.StringSeq  => JAttributeKey.stringArrayKey(key.name)
        case AttributeType.BooleanSeq => JAttributeKey.booleanArrayKey(key.name)
        case AttributeType.LongSeq    => JAttributeKey.longArrayKey(key.name)
        case AttributeType.DoubleSeq  => JAttributeKey.doubleArrayKey(key.name)
      }

    final def toJava(attributes: Attributes): JAttributes =
      toJavaAttributes(attributes)

    def toJavaAttributes(
        attributes: immutable.Iterable[Attribute[_]]
    ): JAttributes = {
      val builder = JAttributes.builder

      attributes.foreach { case Attribute(key, value) =>
        key.`type` match {
          case AttributeType.String =>
            builder.put(key.name, value.asInstanceOf[String])
          case AttributeType.Boolean =>
            builder.put(key.name, value.asInstanceOf[Boolean])
          case AttributeType.Long =>
            builder.put(key.name, value.asInstanceOf[Long])
          case AttributeType.Double =>
            builder.put(key.name, value.asInstanceOf[Double])
          case AttributeType.StringSeq =>
            builder.put(key.name, value.asInstanceOf[Seq[String]]: _*)
          case AttributeType.BooleanSeq =>
            builder.put(key.name, value.asInstanceOf[Seq[Boolean]]: _*)
          case AttributeType.LongSeq =>
            builder.put(key.name, value.asInstanceOf[Seq[Long]]: _*)
          case AttributeType.DoubleSeq =>
            builder.put(key.name, value.asInstanceOf[Seq[Double]]: _*)
        }
      }

      builder.build()
    }

    def toScala(key: JAttributeKey[_]): AttributeKey[_] =
      key.getType match {
        case JAttributeType.STRING        => AttributeKey.string(key.getKey)
        case JAttributeType.BOOLEAN       => AttributeKey.boolean(key.getKey)
        case JAttributeType.LONG          => AttributeKey.long(key.getKey)
        case JAttributeType.DOUBLE        => AttributeKey.double(key.getKey)
        case JAttributeType.STRING_ARRAY  => AttributeKey.stringSeq(key.getKey)
        case JAttributeType.BOOLEAN_ARRAY => AttributeKey.booleanSeq(key.getKey)
        case JAttributeType.LONG_ARRAY    => AttributeKey.longSeq(key.getKey)
        case JAttributeType.DOUBLE_ARRAY  => AttributeKey.doubleSeq(key.getKey)
      }

    def toScala(attributes: JAttributes): Attributes = {
      val builder = Attributes.newBuilder

      attributes.forEach { (key: JAttributeKey[_], value: Any) =>
        key.getType match {
          case JAttributeType.STRING =>
            builder.addOne(key.getKey, value.asInstanceOf[String])
          case JAttributeType.BOOLEAN =>
            builder.addOne(key.getKey, value.asInstanceOf[Boolean])
          case JAttributeType.LONG =>
            builder.addOne(key.getKey, value.asInstanceOf[Long])
          case JAttributeType.DOUBLE =>
            builder.addOne(key.getKey, value.asInstanceOf[Double])
          case JAttributeType.STRING_ARRAY =>
            builder.addOne(
              key.getKey,
              value.asInstanceOf[ju.List[String]].asScala.toSeq
            )
          case JAttributeType.BOOLEAN_ARRAY =>
            builder.addOne(
              key.getKey,
              value.asInstanceOf[ju.List[Boolean]].asScala.toSeq
            )
          case JAttributeType.LONG_ARRAY =>
            builder.addOne(
              key.getKey,
              value.asInstanceOf[ju.List[Long]].asScala.toSeq
            )
          case JAttributeType.DOUBLE_ARRAY =>
            builder.addOne(
              key.getKey,
              value.asInstanceOf[ju.List[Double]].asScala.toSeq
            )
        }
      }

      builder.result()
    }
  }
}
