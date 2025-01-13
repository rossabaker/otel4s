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

package org.typelevel.otel4s.trace

import cats.Show
import cats.kernel.Hash

import scala.collection.immutable.ListMap
import scala.collection.immutable.SeqMap

/** An '''immutable''' representation of the key-value pairs defined by the W3C Trace Context specification.
  *
  * Trace state allows different vendors propagate additional information and interoperate with their legacy Id formats.
  *
  *   - Key is opaque string up to 256 characters printable. It MUST begin with a lowercase letter, and can only contain
  *     lowercase letters a-z, digits 0-9, underscores _, dashes -, asterisks *, and forward slashes /.
  *   - Value is opaque string up to 256 characters printable ASCII RFC0020 characters (i.e., the range 0x20 to 0x7E)
  *     except comma , and =.
  *
  * @see
  *   [[https://opentelemetry.io/docs/specs/otel/trace/api/#tracestate]]
  *   [[https://www.w3.org/TR/trace-context/#mutating-the-tracestate-field]]
  *   [[https://www.w3.org/TR/trace-context/#tracestate-header-field-values]]
  */
sealed trait TraceState {

  /** Returns the value to which the specified key is mapped, or `None` if this map contains no mapping for the key.
    *
    * @param key
    *   with which the specified value is to be associated
    */
  def get(key: String): Option[String]

  /** Adds or updates the entry that has the given `key` if it is present. If either the key or the value is invalid,
    * the entry will not be added.
    *
    * @param key
    *   the key for the entry to be added. Key is an opaque string up to 256 characters printable. It MUST begin with a
    *   lowercase letter, and can only contain lowercase letters a-z, digits 0-9, underscores _, dashes -, asterisks *,
    *   and forward slashes /. For multi-tenant vendor scenarios, an at sign (@) can be used to prefix the vendor name.
    *   The tenant id (before the '@') is limited to 240 characters and the vendor id is limited to 13 characters. If in
    *   the multi-tenant vendor format, then the first character may additionally be numeric.
    *
    * @param value
    *   the value for the entry to be added. Value is opaque string up to 256 characters printable ASCII RFC0020
    *   characters (i.e., the range 0x20 to 0x7E) except comma , and =.
    *
    * @return
    *   a new instance of [[TraceState]] with added entry
    */
  def updated(key: String, value: String): TraceState

  /** Removes the entry that has the given `key` if it is present.
    *
    * @param key
    *   the key for the entry to be removed.
    *
    * @return
    *   a new instance of [[TraceState]] with removed entry
    */
  def removed(key: String): TraceState

  /** Returns the number of entries in this state. */
  def size: Int

  /** Returns whether this state is empty, containing no entries. */
  def isEmpty: Boolean

  /** Returns a map representation of this state. */
  def asMap: SeqMap[String, String]

  override final def hashCode(): Int =
    Hash[TraceState].hash(this)

  override final def equals(obj: Any): Boolean =
    obj match {
      case other: TraceState => Hash[TraceState].eqv(this, other)
      case _                 => false
    }

  override final def toString: String =
    Show[TraceState].show(this)
}

object TraceState {

  private val Empty: TraceState = MapBasedTraceState(Vector.empty)

  /** An empty [[TraceState]]. */
  def empty: TraceState = Empty

  implicit val traceStateHash: Hash[TraceState] =
    Hash.by((_: TraceState).asMap)(Hash.fromUniversalHashCode)

  implicit val traceStateShow: Show[TraceState] =
    Show.show { state =>
      val entries = state.asMap
        .map { case (key, value) => s"$key=$value" }
        .mkString("{", ",", "}")

      s"TraceState{entries=$entries}"
    }

  /** Creates [[TraceState]] from the given map.
    *
    * '''Important''': the map entries will not be validated. Use this method when you 100% sure the entries are valid.
    * For example, when you wrap OpenTelemetry Java trace state.
    */
  private[otel4s] def fromVectorUnsafe(
      entries: Vector[(String, String)]
  ): TraceState =
    MapBasedTraceState(entries)

  private final case class MapBasedTraceState(
      private val entries: Vector[(String, String)]
  ) extends TraceState {
    import Validation._

    lazy val asMap: SeqMap[String, String] =
      ListMap.from(entries)

    def updated(key: String, value: String): TraceState =
      if (isKeyValid(key) && isValueValid(value)) {
        val withoutKey = entries.filterNot(_._1 == key)
        if (withoutKey.sizeIs < MaxEntries) {
          copy(entries = withoutKey.prepended(key -> value))
        } else {
          this
        }
      } else {
        this
      }

    def removed(key: String): TraceState =
      copy(entries = entries.filterNot(_._1 == key))

    def get(key: String): Option[String] =
      entries.collectFirst { case (k, value) if k == key => value }

    def size: Int = entries.size
    def isEmpty: Boolean = entries.isEmpty
  }

  private object Validation {
    final val MaxEntries = 32
    private final val KeyMaxSize = 256
    private final val ValueMaxSize = 256
    private final val TenantIdMaxSize = 240
    private final val VendorIdSize = 13

    /** Key is an opaque string up to 256 characters printable. It MUST begin with a lowercase letter, and can only
      * contain lowercase letters a-z, digits 0-9, underscores _, dashes -, asterisks *, and forward slashes /. For
      * multi-tenant vendor scenarios, an at sign (@) can be used to prefix the vendor name. The tenant id (before the
      * '@') is limited to 240 characters and the vendor id is limited to 13 characters. If in the multi-tenant vendor
      * format, then the first character may additionally be numeric.
      *
      * @see
      *   [[https://opentelemetry.io/docs/specs/otel/trace/api/#tracestate]]
      *   [[https://www.w3.org/TR/trace-context/#mutating-the-tracestate-field]]
      *   [[https://www.w3.org/TR/trace-context/#tracestate-header-field-values]]
      */
    def isKeyValid(key: String): Boolean = {
      val length = key.length

      // _1 - valid or not
      // _2 - isMultiTenant
      @scala.annotation.tailrec
      def loop(idx: Int, isMultiTenant: Boolean): (Boolean, Boolean) = {
        if (idx < length) {
          val char = key.charAt(idx)

          if (char == '@') {
            // you can't have 2 '@' signs
            if (isMultiTenant) {
              (false, isMultiTenant)
              // tenant id (the part to the left of the '@' sign) must be 240 characters or less
            } else if (idx > TenantIdMaxSize) {
              (false, isMultiTenant)
            } else {
              // vendor id (the part to the right of the '@' sign) must be 1-13 characters long
              val remaining = length - idx - 1
              if (remaining > VendorIdSize || remaining == 0)
                (false, isMultiTenant)
              else loop(idx + 1, isMultiTenant = true)
            }
          } else {
            if (isValidKeyChar(char)) loop(idx + 1, isMultiTenant)
            else (false, isMultiTenant)
          }
        } else {
          (true, isMultiTenant)
        }
      }

      @inline def isValidFirstChar: Boolean =
        key.charAt(0).isDigit || isLowercaseLetter(key.charAt(0))

      @inline def isValidKey: Boolean = {
        val (isValid, isMultiTenant) = loop(1, isMultiTenant = false)
        // if it's not the vendor format (with an '@' sign), the key must start with a letter
        isValid && (isMultiTenant || isLowercaseLetter(key.charAt(0)))
      }

      length > 0 && length <= KeyMaxSize && isValidFirstChar && isValidKey
    }

    @inline private def isLowercaseLetter(char: Char): Boolean =
      char >= 'a' && char <= 'z'

    @inline private def isValidKeyChar(char: Char): Boolean =
      char.isDigit ||
        isLowercaseLetter(char) ||
        char == '_' ||
        char == '-' ||
        char == '*' ||
        char == '/'

    /** Value is opaque string up to 256 characters printable ASCII RFC0020 characters (i.e., the range 0x20 to 0x7E)
      * except comma , and =.
      */
    @inline def isValueValid(value: String): Boolean = {
      val length = value.length
      length > 0 && length <= ValueMaxSize && value.forall { char =>
        char >= 0x20 && char <= 0x7e && char != ',' && char != '='
      }
    }
  }

}
