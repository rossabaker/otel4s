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
package oteljava
package metrics

import cats.effect.kernel.Resource
import cats.effect.kernel.Sync
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.opentelemetry.api.metrics.{Meter => JMeter}
import org.typelevel.otel4s.metrics._

import scala.concurrent.duration.TimeUnit
import scala.jdk.CollectionConverters._

private[oteljava] case class HistogramBuilderImpl[F[_], A](
    factory: HistogramBuilderImpl.Factory[F, A],
    name: String,
    unit: Option[String] = None,
    description: Option[String] = None,
    boundaries: Option[BucketBoundaries] = None
) extends Histogram.Builder[F, A] {

  def withUnit(unit: String): Histogram.Builder[F, A] =
    copy(unit = Option(unit))

  def withDescription(description: String): Histogram.Builder[F, A] =
    copy(description = Option(description))

  def withExplicitBucketBoundaries(
      boundaries: BucketBoundaries
  ): Histogram.Builder[F, A] =
    copy(boundaries = Some(boundaries))

  def of[B: MeasurementValue]: Histogram.Builder[F, B] =
    copy(factory = factory.switchType[B])

  def create: F[Histogram[F, A]] =
    factory.create(name, unit, description, boundaries)

}

object HistogramBuilderImpl {

  def apply[F[_]: Sync, A: MeasurementValue](
      jMeter: JMeter,
      name: String
  ): Histogram.Builder[F, A] =
    MeasurementValue[A] match {
      case MeasurementValue.LongMeasurementValue(cast) =>
        HistogramBuilderImpl(longFactory(jMeter, cast), name)

      case MeasurementValue.DoubleMeasurementValue(cast) =>
        HistogramBuilderImpl(doubleFactory(jMeter, cast), name)
    }

  private[oteljava] trait Factory[F[_], A] {
    def create(
        name: String,
        unit: Option[String],
        description: Option[String],
        boundaries: Option[BucketBoundaries]
    ): F[Histogram[F, A]]

    def switchType[B: MeasurementValue]: Factory[F, B]
  }

  private def longFactory[F[_]: Sync, A](
      jMeter: JMeter,
      cast: A => Long
  ): Factory[F, A] =
    new Factory[F, A] {
      def create(
          name: String,
          unit: Option[String],
          description: Option[String],
          boundaries: Option[BucketBoundaries]
      ): F[Histogram[F, A]] =
        Sync[F].delay {
          val builder = jMeter.histogramBuilder(name)
          unit.foreach(builder.setUnit)
          description.foreach(builder.setDescription)
          boundaries.foreach(b =>
            builder.setExplicitBucketBoundariesAdvice(
              b.boundaries.map(Double.box).asJava
            )
          )
          val histogram = builder.ofLongs().build

          val backend = new Histogram.Backend[F, A] {
            val meta: Histogram.Meta[F] = Histogram.Meta.enabled

            def record(value: A, attributes: Attribute[_]*): F[Unit] =
              Sync[F].delay(
                histogram.record(
                  cast(value),
                  Conversions.toJAttributes(attributes)
                )
              )

            def recordDuration(
                timeUnit: TimeUnit,
                attributes: Attribute[_]*
            ): Resource[F, Unit] =
              Resource
                .makeCase(Sync[F].monotonic) { case (start, ec) =>
                  for {
                    end <- Sync[F].monotonic
                    _ <- Sync[F].delay(
                      histogram.record(
                        (end - start).toUnit(timeUnit).toLong,
                        Conversions.toJAttributes(
                          attributes ++ Histogram.causeAttributes(ec)
                        )
                      )
                    )
                  } yield ()
                }
                .void
          }

          Histogram.fromBackend(backend)
        }

      def switchType[B: MeasurementValue]: Factory[F, B] =
        MeasurementValue[B] match {
          case MeasurementValue.LongMeasurementValue(cast) =>
            longFactory(jMeter, cast)
          case MeasurementValue.DoubleMeasurementValue(cast) =>
            doubleFactory(jMeter, cast)
        }
    }

  private def doubleFactory[F[_]: Sync, A](
      jMeter: JMeter,
      cast: A => Double
  ): Factory[F, A] =
    new Factory[F, A] {
      def create(
          name: String,
          unit: Option[String],
          description: Option[String],
          boundaries: Option[BucketBoundaries]
      ): F[Histogram[F, A]] =
        Sync[F].delay {
          val builder = jMeter.histogramBuilder(name)
          unit.foreach(builder.setUnit)
          description.foreach(builder.setDescription)
          boundaries.foreach(b =>
            builder.setExplicitBucketBoundariesAdvice(
              b.boundaries.map(Double.box).asJava
            )
          )
          val histogram = builder.build

          val backend = new Histogram.Backend[F, A] {
            val meta: Histogram.Meta[F] = Histogram.Meta.enabled

            def record(value: A, attributes: Attribute[_]*): F[Unit] =
              Sync[F].delay(
                histogram.record(
                  cast(value),
                  Conversions.toJAttributes(attributes)
                )
              )

            def recordDuration(
                timeUnit: TimeUnit,
                attributes: Attribute[_]*
            ): Resource[F, Unit] =
              Resource
                .makeCase(Sync[F].monotonic) { case (start, ec) =>
                  for {
                    end <- Sync[F].monotonic
                    _ <- Sync[F].delay(
                      histogram.record(
                        (end - start).toUnit(timeUnit),
                        Conversions.toJAttributes(
                          attributes ++ Histogram.causeAttributes(ec)
                        )
                      )
                    )
                  } yield ()
                }
                .void
          }

          Histogram.fromBackend(backend)
        }

      def switchType[B: MeasurementValue]: Factory[F, B] =
        MeasurementValue[B] match {
          case MeasurementValue.LongMeasurementValue(cast) =>
            longFactory(jMeter, cast)
          case MeasurementValue.DoubleMeasurementValue(cast) =>
            doubleFactory(jMeter, cast)
        }
    }

}
