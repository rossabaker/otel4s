/*
 * Copyright 2023 Typelevel
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
package sdk

import cats.Hash
import cats.Show
import cats.syntax.all._
import org.typelevel.otel4s.sdk.TelemetryResource.ResourceInitializationError
import org.typelevel.otel4s.semconv.attributes.ServiceAttributes
import org.typelevel.otel4s.semconv.attributes.TelemetryAttributes

/** [[TelemetryResource]] serves as a representation of a resource that captures essential identifying information
  * regarding the entities associated with reported signals, such as statistics or traces.
  *
  * @see
  *   [[https://opentelemetry.io/docs/specs/otel/overview/#resources]]
  *
  * @see
  *   [[https://opentelemetry.io/docs/specs/otel/resource/sdk]]
  */
sealed trait TelemetryResource {

  /** An [[Attributes]] associated with the resource.
    */
  def attributes: Attributes

  /** An optional schema URL associated with the resource.
    */
  def schemaUrl: Option[String]

  /** Merges [[TelemetryResource]] into another [[TelemetryResource]].
    *
    * Schema URL merge outcomes:
    *   - if `this` resource's schema URL is empty then the `other` resource's schema URL will be selected
    *   - if `other` resource's schema URL is empty then `this` resource's schema URL will be selected
    *   - if `this` and `other` resources have the same non-empty schema URL then this schema URL will be selected
    *   - if `this` and `other` resources have different non-empty schema URLs then the result will be a merge error
    *
    * @note
    *   if the same attribute exists in both resources, the attribute from the `other` [[TelemetryResource]] will be
    *   retained.
    *
    * @param other
    *   the other [[TelemetryResource]] to merge with
    *
    * @return
    *   a new [[TelemetryResource]] with the merged attributes
    */
  def merge(
      other: TelemetryResource
  ): Either[ResourceInitializationError, TelemetryResource]

  /** Unsafe version of [[merge]] which throws an exception if the merge fails.
    *
    * @param other
    *   the other [[TelemetryResource]] to merge with
    */
  def mergeUnsafe(other: TelemetryResource): TelemetryResource

  override final def hashCode(): Int =
    Hash[TelemetryResource].hash(this)

  override final def equals(obj: Any): Boolean =
    obj match {
      case other: TelemetryResource => Hash[TelemetryResource].eqv(this, other)
      case _                        => false
    }

  override final def toString: String =
    Show[TelemetryResource].show(this)

}

object TelemetryResource {

  private val Empty: TelemetryResource =
    TelemetryResource(Attributes.empty, None)

  private val Default: TelemetryResource = {
    val telemetrySdk = Attributes(
      TelemetryAttributes.TelemetrySdkName("otel4s"),
      TelemetryAttributes.TelemetrySdkLanguage(
        TelemetryAttributes.TelemetrySdkLanguageValue.Scala.value
      ),
      TelemetryAttributes.TelemetrySdkVersion(BuildInfo.version)
    )

    val mandatory = Attributes(
      ServiceAttributes.ServiceName("unknown_service:scala")
    )

    TelemetryResource(telemetrySdk |+| mandatory, None)
  }

  sealed abstract class ResourceInitializationError extends Throwable
  object ResourceInitializationError {
    case object SchemaUrlConflict extends ResourceInitializationError
  }

  /** Creates a [[TelemetryResource]] with the given `attributes`. The `schemaUrl` will be `None.`
    *
    * @param attributes
    *   the attributes to associate with the resource
    */
  def apply(attributes: Attributes): TelemetryResource =
    Impl(attributes, None)

  /** Creates a [[TelemetryResource]] with the given `attributes` and `schemaUrl`.
    *
    * @param attributes
    *   the attributes to associate with the resource
    * @param schemaUrl
    *   schema URL to associate with the result
    */
  def apply(
      attributes: Attributes,
      schemaUrl: Option[String]
  ): TelemetryResource =
    Impl(attributes, schemaUrl)

  /** Returns an empty [[TelemetryResource]].
    *
    * It is strongly recommended to start with [[TelemetryResource.default]] instead of this method to include SDK
    * required attributes.
    */
  def empty: TelemetryResource = Empty

  /** Returns the default [[TelemetryResource]]. This resource contains the default attributes provided by the SDK.
    */
  def default: TelemetryResource = Default

  implicit val showResource: Show[TelemetryResource] =
    r => show"TelemetryResource{attributes=${r.attributes}, schemaUrl=${r.schemaUrl}}"

  implicit val hashResource: Hash[TelemetryResource] =
    Hash.by(r => (r.attributes, r.schemaUrl))

  private final case class Impl(
      attributes: Attributes,
      schemaUrl: Option[String]
  ) extends TelemetryResource {

    def merge(
        other: TelemetryResource
    ): Either[ResourceInitializationError, TelemetryResource] =
      if (other == TelemetryResource.Empty) Right(this)
      else if (this == TelemetryResource.Empty) Right(other)
      else {
        (other.schemaUrl, schemaUrl) match {
          case (Some(otherUrl), Some(url)) if otherUrl != url =>
            Left(ResourceInitializationError.SchemaUrlConflict)

          case (otherUrl, url) =>
            Right(Impl(attributes |+| other.attributes, otherUrl.orElse(url)))
        }
      }

    def mergeUnsafe(other: TelemetryResource): TelemetryResource =
      merge(other).fold(throw _, identity)

  }

}
