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

import cats.Monad
import cats.effect.IO
import cats.effect.IOApp
import cats.effect.Resource
import cats.effect.std.Console
import cats.syntax.all._
import io.opentelemetry.api.GlobalOpenTelemetry
import org.typelevel.otel4s.java.OtelJava
import org.typelevel.otel4s.trace.Tracer

import scala.concurrent.duration._

trait Work[F[_]] {
  def doWork: F[Unit]
}

object Work {
  def apply[F[_]: Monad: Tracer: Console]: Work[F] =
    new Work[F] {
      def doWork: F[Unit] =
        Tracer[F].span("Work.DoWork").use { span =>
          span.addEvent("Starting the work.") *>
            doWorkInternal *>
            span.addEvent("Finished working.")
        }

      def doWorkInternal =
        Tracer[F].span("Work.InternalWork").surround(
          Console[F].println("Doin' work")
        )
    }
}

object TracingExample extends IOApp.Simple {
  def tracerResource: Resource[IO, Tracer[IO]] =
    Resource
      .eval(IO(GlobalOpenTelemetry.get))
      .evalMap(OtelJava.forSync[IO])
      .evalMap(_.tracerProvider.tracer("Example").get)

  def run: IO[Unit] = {
    tracerResource.use { implicit tracer: Tracer[IO] =>
      val resource: Resource[IO, Unit] = Resource.make(IO.sleep(50.millis))(_ => IO.sleep(100.millis))
      tracer.resourceSpan("resource")(resource).surround(Work[IO].doWork)
    }
  }
}
