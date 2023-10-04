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

package org.typelevel.otel4s
package java

import cats.effect.Sync
import io.opentelemetry.context.propagation.{
  TextMapPropagator => JTextMapPropagator
}
import org.typelevel.otel4s.java.Conversions._
import org.typelevel.otel4s.java.context.Context

private[java] class TextMapPropagatorImpl[F[_]: Sync](
    jPropagator: JTextMapPropagator
) extends TextMapPropagator[F, Context] {
  def extract[A: TextMapGetter](ctx: Context, carrier: A): Context =
    ctx.map(jPropagator.extract(_, carrier, fromTextMapGetter))

  def inject[A: TextMapSetter](ctx: Context, carrier: A): F[Unit] =
    Sync[F].delay(
      jPropagator.inject(ctx.underlying, carrier, fromTextMapSetter)
    )

  def injected[A](ctx: Context, carrier: A)(implicit
      injector: TextMapUpdater[A]
  ): A = {
    var injectedCarrier = carrier
    jPropagator.inject[Null](
      ctx.underlying,
      null, // explicitly allowed per opentelemetry-java, so our setter can be a lambda!
      (_, key, value) => {
        injectedCarrier = injector.updated(injectedCarrier, key, value)
      }
    )
    injectedCarrier
  }
}
