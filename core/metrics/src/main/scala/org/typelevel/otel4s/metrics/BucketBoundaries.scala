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

package org.typelevel.otel4s.metrics

/** Represents bucket boundaries of a [[Histogram]] instrument.
  */
sealed trait BucketBoundaries {
  def boundaries: Vector[Double]
}

object BucketBoundaries {

  /** Creates [[BucketBoundaries]] using the given `boundaries`.
    *
    * Throws an exception if any of the following rules is violated:
    *   - the boundary cannot be `Double.NaN`
    *   - the boundaries must be in increasing order
    *   - first boundary cannot be `Double.NegativeInfinity`
    *   - last boundary cannot be `Double.PositiveInfinity`
    *
    * @param boundaries
    *   the vector of bucket boundaries
    */
  def apply(boundaries: Vector[Double]): BucketBoundaries = {
    require(
      boundaries.forall(b => !b.isNaN),
      "bucket boundary cannot be NaN"
    )

    require(
      boundaries.sliding(2).forall(p => p(0) < p(1)),
      "bucket boundaries must be in increasing oder"
    )

    require(
      boundaries.isEmpty || boundaries.head.isNegInfinity,
      "first boundary cannot be -Inf"
    )
    require(
      boundaries.isEmpty || boundaries.last.isPosInfinity,
      "last boundary cannot be +Inf"
    )

    Impl(boundaries)
  }

  private final case class Impl(
      boundaries: Vector[Double]
  ) extends BucketBoundaries
}
