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
package metrics

private[otel4s] trait UpDownCounterMacro[F[_], A] {
  def backend: UpDownCounter.Backend[F, A]

  /** Records a value with a set of attributes.
    *
    * @param value
    *   the value to add to the counter
    *
    * @param attributes
    *   the set of attributes to associate with the value
    */
  def add(value: A, attributes: Attribute[_]*): F[Unit] =
    macro UpDownCounterMacro.add[A]

  /** Increments a counter by one.
    *
    * @param attributes
    *   the set of attributes to associate with the value
    */
  def inc(attributes: Attribute[_]*): F[Unit] =
    macro UpDownCounterMacro.inc

  /** Decrements a counter by one.
    *
    * @param attributes
    *   the set of attributes to associate with the value
    */
  def dec(attributes: Attribute[_]*): F[Unit] =
    macro UpDownCounterMacro.dec

}

object UpDownCounterMacro {
  import scala.reflect.macros.blackbox

  def add[A](c: blackbox.Context)(
      value: c.Expr[A],
      attributes: c.Expr[Attribute[_]]*
  ): c.universe.Tree = {
    import c.universe._
    val backend = q"${c.prefix}.backend"
    val meta = q"$backend.meta"

    q"if ($meta.isEnabled) $backend.add($value, ..$attributes) else $meta.unit"
  }

  def inc(c: blackbox.Context)(
      attributes: c.Expr[Attribute[_]]*
  ): c.universe.Tree = {
    import c.universe._
    val backend = q"${c.prefix}.backend"
    val meta = q"$backend.meta"

    q"if ($meta.isEnabled) $backend.inc(..$attributes) else $meta.unit"
  }

  def dec(c: blackbox.Context)(
      attributes: c.Expr[Attribute[_]]*
  ): c.universe.Tree = {
    import c.universe._
    val backend = q"${c.prefix}.backend"
    val meta = q"$backend.meta"

    q"if ($meta.isEnabled) $backend.dec(..$attributes) else $meta.unit"
  }

}
