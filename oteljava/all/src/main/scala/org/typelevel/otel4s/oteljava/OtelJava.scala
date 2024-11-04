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

package org.typelevel.otel4s.oteljava

import cats.Applicative
import cats.effect.Async
import cats.effect.Resource
import cats.effect.Sync
import cats.syntax.all._
import io.opentelemetry.api.{OpenTelemetry => JOpenTelemetry}
import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.sdk.{OpenTelemetrySdk => JOpenTelemetrySdk}
import io.opentelemetry.sdk.autoconfigure.{AutoConfiguredOpenTelemetrySdk => AutoConfigOtelSdk}
import io.opentelemetry.sdk.autoconfigure.{AutoConfiguredOpenTelemetrySdkBuilder => AutoConfigOtelSdkBuilder}
import io.opentelemetry.sdk.common.CompletableResultCode
import org.typelevel.otel4s.Otel4s
import org.typelevel.otel4s.context.LocalProvider
import org.typelevel.otel4s.context.propagation.ContextPropagators
import org.typelevel.otel4s.metrics.MeterProvider
import org.typelevel.otel4s.oteljava.context.Context
import org.typelevel.otel4s.oteljava.context.LocalContext
import org.typelevel.otel4s.oteljava.context.LocalContextProvider
import org.typelevel.otel4s.oteljava.context.propagation.PropagatorConverters._
import org.typelevel.otel4s.oteljava.metrics.Metrics
import org.typelevel.otel4s.oteljava.trace.Traces
import org.typelevel.otel4s.trace.TracerProvider

final class OtelJava[F[_]] private (
    val underlying: JOpenTelemetry,
    val propagators: ContextPropagators[Context],
    val meterProvider: MeterProvider[F],
    val tracerProvider: TracerProvider[F],
)(implicit val localContext: LocalContext[F])
    extends Otel4s[F] {

  type Ctx = Context

  override def toString: String = s"OtelJava{$underlying}"
}

object OtelJava {

  /** Creates an [[org.typelevel.otel4s.Otel4s]] from a Java OpenTelemetry instance.
    *
    * @param jOtel
    *   A Java OpenTelemetry instance. It is the caller's responsibility to shut this down. Failure to do so may result
    *   in lost metrics and traces.
    *
    * @return
    *   An effect of an [[org.typelevel.otel4s.Otel4s]] resource.
    */
  def forAsync[F[_]: Async: LocalContextProvider](
      jOtel: JOpenTelemetry
  ): F[OtelJava[F]] =
    LocalProvider[F, Context].local.map { implicit l =>
      local[F](jOtel)
    }

  def local[F[_]: Async: LocalContext](
      jOtel: JOpenTelemetry
  ): OtelJava[F] = {
    val contextPropagators = jOtel.getPropagators.asScala

    val metrics = Metrics.forAsync(jOtel)
    val traces = Traces.local(jOtel, contextPropagators)
    new OtelJava[F](
      jOtel,
      contextPropagators,
      metrics.meterProvider,
      traces.tracerProvider,
    )
  }

  /** Creates a no-op implementation of the [[OtelJava]].
    */
  def noop[F[_]: Applicative: LocalContextProvider]: F[OtelJava[F]] =
    for {
      local <- LocalProvider[F, Context].local
    } yield new OtelJava(
      JOpenTelemetry.noop(),
      ContextPropagators.noop,
      MeterProvider.noop,
      TracerProvider.noop
    )(local)

  /** Lifts the acquisition of a Java OpenTelemetrySdk instance to a Resource.
    *
    * @param acquire
    *   OpenTelemetrySdk resource
    *
    * @return
    *   An [[org.typelevel.otel4s.Otel4s]] resource.
    */
  def resource[F[_]: Async: LocalContextProvider](
      acquire: F[JOpenTelemetrySdk]
  ): Resource[F, OtelJava[F]] =
    Resource
      .make(acquire)(sdk => asyncFromCompletableResultCode(Sync[F].delay(sdk.shutdown())))
      .evalMap(forAsync[F])

  /** Creates a [[cats.effect.Resource `Resource`]] of the automatic configuration of a Java `OpenTelemetrySdk`
    * instance.
    *
    * If you rely on
    * [[https://opentelemetry.io/docs/instrumentation/java/automatic/ automatic instrumentation via Java agent]], you
    * MUST NOT use this method and MUST use [[global]] instead.
    *
    * @param customize
    *   A function for customizing the auto-configured SDK builder. This function MUST NOT call `setResultAsGlobal`.
    * @return
    *   An [[org.typelevel.otel4s.Otel4s]] resource.
    * @see
    *   [[global]]
    */
  def autoConfigured[F[_]: Async: LocalContextProvider](
      customize: AutoConfigOtelSdkBuilder => AutoConfigOtelSdkBuilder = identity
  ): Resource[F, OtelJava[F]] =
    resource {
      Sync[F].delay {
        customize(AutoConfigOtelSdk.builder())
          .disableShutdownHook()
          .build()
          .getOpenTelemetrySdk
      }
    }

  /** Creates an [[org.typelevel.otel4s.Otel4s]] from the global Java OpenTelemetry instance.
    *
    * @see
    *   [[autoConfigured]]
    */
  def global[F[_]: Async: LocalContextProvider]: F[OtelJava[F]] =
    Sync[F].delay(GlobalOpenTelemetry.get).flatMap(forAsync[F])

  private[this] def asyncFromCompletableResultCode[F[_]](
      codeF: F[CompletableResultCode],
      msg: => Option[String] = None
  )(implicit F: Async[F]): F[Unit] =
    F.flatMap(codeF)(code =>
      F.async[Unit](cb =>
        F.delay {
          code.whenComplete(() =>
            if (code.isSuccess())
              cb(Either.unit)
            else
              cb(
                Left(
                  new RuntimeException(
                    msg.getOrElse(
                      "OpenTelemetry SDK async operation failed"
                    )
                  )
                )
              )
          )
          None
        }
      )
    )
}
