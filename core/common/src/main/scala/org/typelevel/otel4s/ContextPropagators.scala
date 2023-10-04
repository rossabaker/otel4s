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

import cats.Applicative

trait ContextPropagators[F[_], Ctx] {
  def textMapPropagator: TextMapPropagator[F, Ctx]
}

object ContextPropagators {

  /** Creates a no-op implementation of the [[ContextPropagators]].
    *
    * A [[TextMapPropagator]] has no-op implementation too.
    *
    * @tparam F
    *   the higher-kinded type of a polymorphic effect
    */
  def noop[F[_]: Applicative, Ctx]: ContextPropagators[F, Ctx] =
    new ContextPropagators[F, Ctx] {
      def textMapPropagator: TextMapPropagator[F, Ctx] =
        TextMapPropagator.noop
    }
}
