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

package org.typelevel.otel4s.java.trace

import cats.effect.Sync
import io.opentelemetry.api.trace.{TracerProvider => JTracerProvider}
import org.typelevel.otel4s.context.propagation.ContextPropagators
import org.typelevel.otel4s.java.context.Context
import org.typelevel.otel4s.java.context.LocalContext
import org.typelevel.otel4s.trace.TracerBuilder
import org.typelevel.otel4s.trace.TracerProvider

private[java] class TracerProviderImpl[F[_]: Sync: LocalContext] private (
    jTracerProvider: JTracerProvider,
    propagators: ContextPropagators[Context]
) extends TracerProvider[F] {
  def tracer(name: String): TracerBuilder[F] =
    TracerBuilderImpl(jTracerProvider, propagators, name)
}

private[java] object TracerProviderImpl {

  def local[F[_]: Sync: LocalContext](
      jTracerProvider: JTracerProvider,
      propagators: ContextPropagators[Context]
  ): TracerProvider[F] =
    new TracerProviderImpl(jTracerProvider, propagators)
}
