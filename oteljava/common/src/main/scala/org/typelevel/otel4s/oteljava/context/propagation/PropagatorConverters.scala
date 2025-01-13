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

package org.typelevel.otel4s.oteljava.context
package propagation

import io.opentelemetry.context.propagation.{ContextPropagators => JContextPropagators}
import io.opentelemetry.context.propagation.{TextMapGetter => JTextMapGetter}
import io.opentelemetry.context.propagation.{TextMapPropagator => JTextMapPropagator}
import org.typelevel.otel4s.context.propagation.ContextPropagators
import org.typelevel.otel4s.context.propagation.TextMapGetter
import org.typelevel.otel4s.context.propagation.TextMapPropagator

import scala.{unchecked => uc}

/** This object provides `asScala` and `asJava` extension methods that convert between Scala and Java `TextMapGetter` s,
  * `TextMapPropagator` s, and `ContextPropagators`.
  *
  * {{{
  *   import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
  *   import org.typelevel.otel4s.context.propagation.TextMapPropagator
  *   import org.typelevel.otel4s.oteljava.context.Context
  *   import org.typelevel.otel4s.oteljava.context.propagation.TextMapOperatorConverters._
  *
  *   val propagator: TextMapPropagator[Context] = W3CTraceContextPropagator.getInstance().asScala
  * }}}
  *
  * The conversions return wrappers for the TextMap operators, and converting from a source type to a target type and
  * back again will return the original source object. For example:
  *
  * {{{
  *   import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
  *   import org.typelevel.otel4s.context.propagation.TextMapPropagator
  *   import org.typelevel.otel4s.oteljava.context.Context
  *   import org.typelevel.otel4s.oteljava.context.propagation.TextMapOperatorConverters._
  *
  *   val source: W3CTraceContextPropagator = W3CTraceContextPropagator.getInstance()
  *   val target: TextMapPropagator[Context] = source.asScala
  *   val other: io.opentelemetry.context.propagation.TextMapPropagator = target.asJava
  *   assert(source eq other)
  * }}}
  *
  * Currently, `ContextPropagators` for both Java and Scala are simple wrappers around a `TextMapPropagator` instance of
  * the corresponding type. Consequently, conversions between `ContextPropagators` convert the `TextMapPropagator` and
  * do not use a custom wrapper.
  */
object PropagatorConverters {

  implicit final class TextMapGetterHasAsJava[A](
      private val getter: TextMapGetter[A]
  ) extends AnyVal {

    /** Converts a Scala `TextMapGetter` to a Java `TextMapGetter`.
      *
      * The returned Java `TextMapGetter` is backed by the provided Scala `TextMapGetter` unless the Scala
      * `TextMapGetter` was previously obtained from an implicit or explicit call of `asScala`, in which case the
      * original Java `TextMapGetter` will be returned.
      */
    def asJava: JTextMapGetter[A] = Explicit.asJava(getter)
  }

  implicit final class TextMapPropagatorHasAsJava(
      private val prop: TextMapPropagator[Context]
  ) extends AnyVal {

    /** Converts a Scala `TextMapPropagator` to a Java `TextMapPropagator`.
      *
      * The returned Java `TextMapPropagator` is backed by the provided Scala `TextMapPropagator` unless the Scala
      * `TextMapPropagator` was previously obtained from an implicit or explicit call of `asScala`, in which case the
      * original Java `TextMapPropagator` will be returned.
      */
    def asJava: JTextMapPropagator = Explicit.asJava(prop)
  }

  implicit final class ContextPropagatorsHasAsJava(
      private val cp: ContextPropagators[Context]
  ) extends AnyVal {

    /** Converts a Scala `ContextPropagators` to a Java `ContextPropagators`. */
    def asJava: JContextPropagators = Explicit.asJava(cp)
  }

  implicit final class TextMapGetterHasAsScala[A](
      private val getter: JTextMapGetter[A]
  ) extends AnyVal {

    /** Converts a Java `TextMapGetter` to a Scala `TextMapGetter`.
      *
      * The returned Scala `TextMapGetter` is backed by the provided Java `TextMapGetter` unless the Java
      * `TextMapGetter` was previously obtained from an implicit or explicit call of `asJava`, in which case the
      * original Scala `TextMapGetter` will be returned.
      */
    def asScala: TextMapGetter[A] = Explicit.asScala(getter)
  }

  implicit final class TextMapPropagatorHasAsScala(
      private val propagator: JTextMapPropagator
  ) extends AnyVal {

    /** Converts a Java `TextMapPropagator` to a Scala `TextMapPropagator`.
      *
      * The returned Scala `TextMapPropagator` is backed by the provided Java `TextMapPropagator` unless the Java
      * `TextMapPropagator` was previously obtained from an implicit or explicit call of `asJava`, in which case the
      * original Scala `TextMapPropagator` will be returned.
      */
    def asScala: TextMapPropagator[Context] = Explicit.asScala(propagator)
  }

  implicit final class ContextPropagatorsHasAsScala(
      private val cp: JContextPropagators
  ) extends AnyVal {

    /** Converts a Java `ContextPropagators` to a Scala `ContextPropagators`. */
    def asScala: ContextPropagators[Context] = Explicit.asScala(cp)
  }

  private[propagation] object Explicit {
    import JavaPropagatorWrappers._

    def asJava[A](getter: TextMapGetter[A]): JTextMapGetter[A] = getter match {
      case null                                  => null
      case wrapper: JTextMapGetterWrapper[A @uc] => wrapper.underlying
      case _                                     => new TextMapGetterWrapper(getter)
    }

    def asJava(propagator: TextMapPropagator[Context]): JTextMapPropagator =
      propagator match {
        case null                               => null
        case wrapper: JTextMapPropagatorWrapper => wrapper.underlying
        case _                                  => new TextMapPropagatorWrapper(propagator)
      }

    def asJava(propagators: ContextPropagators[Context]): JContextPropagators =
      JContextPropagators.create(asJava(propagators.textMapPropagator))

    def asScala[A](getter: JTextMapGetter[A]): TextMapGetter[A] = getter match {
      case null                                 => null
      case wrapper: TextMapGetterWrapper[A @uc] => wrapper.underlying
      case _                                    => new JTextMapGetterWrapper(getter)
    }

    def asScala(propagator: JTextMapPropagator): TextMapPropagator[Context] =
      propagator match {
        case null                              => null
        case wrapper: TextMapPropagatorWrapper => wrapper.underlying
        case _                                 => new JTextMapPropagatorWrapper(propagator)
      }

    def asScala(propagators: JContextPropagators): ContextPropagators[Context] =
      ContextPropagators.of(asScala(propagators.getTextMapPropagator))
  }
}
