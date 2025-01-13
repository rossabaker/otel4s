/*
 * Copyright 2024 Typelevel
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
package semconv
package experimental
package metrics

import cats.effect.Resource
import org.typelevel.otel4s.metrics._
import org.typelevel.otel4s.semconv.attributes._
import org.typelevel.otel4s.semconv.experimental.attributes._

// DO NOT EDIT, this is an Auto-generated file from buildscripts/templates/registry/otel4s/metrics/SemanticMetrics.scala.j2
object DbExperimentalMetrics {

  @annotation.nowarn("cat=deprecation")
  val specs: List[MetricSpec] = List(
    ClientConnectionCount,
    ClientConnectionCreateTime,
    ClientConnectionIdleMax,
    ClientConnectionIdleMin,
    ClientConnectionMax,
    ClientConnectionPendingRequests,
    ClientConnectionTimeouts,
    ClientConnectionUseTime,
    ClientConnectionWaitTime,
    ClientConnectionsCreateTime,
    ClientConnectionsIdleMax,
    ClientConnectionsIdleMin,
    ClientConnectionsMax,
    ClientConnectionsPendingRequests,
    ClientConnectionsTimeouts,
    ClientConnectionsUsage,
    ClientConnectionsUseTime,
    ClientConnectionsWaitTime,
    ClientCosmosdbActiveInstanceCount,
    ClientCosmosdbOperationRequestCharge,
    ClientOperationDuration,
    ClientResponseReturnedRows,
  )

  /** The number of connections that are currently in state described by the `state` attribute
    */
  object ClientConnectionCount extends MetricSpec {

    val name: String = "db.client.connection.count"
    val description: String = "The number of connections that are currently in state described by the `state` attribute"
    val unit: String = "{connection}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      /** The state of a connection in the pool
        */
      val dbClientConnectionState: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionState,
          List(
            "idle",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
          dbClientConnectionState,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** The time it took to create a new connection
    */
  object ClientConnectionCreateTime extends MetricSpec {

    val name: String = "db.client.connection.create_time"
    val description: String = "The time it took to create a new connection"
    val unit: String = "s"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** The maximum number of idle open connections allowed
    */
  object ClientConnectionIdleMax extends MetricSpec {

    val name: String = "db.client.connection.idle.max"
    val description: String = "The maximum number of idle open connections allowed"
    val unit: String = "{connection}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** The minimum number of idle open connections allowed
    */
  object ClientConnectionIdleMin extends MetricSpec {

    val name: String = "db.client.connection.idle.min"
    val description: String = "The minimum number of idle open connections allowed"
    val unit: String = "{connection}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** The maximum number of open connections allowed
    */
  object ClientConnectionMax extends MetricSpec {

    val name: String = "db.client.connection.max"
    val description: String = "The maximum number of open connections allowed"
    val unit: String = "{connection}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** The number of current pending requests for an open connection
    */
  object ClientConnectionPendingRequests extends MetricSpec {

    val name: String = "db.client.connection.pending_requests"
    val description: String = "The number of current pending requests for an open connection"
    val unit: String = "{request}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** The number of connection timeouts that have occurred trying to obtain a connection from the pool
    */
  object ClientConnectionTimeouts extends MetricSpec {

    val name: String = "db.client.connection.timeouts"
    val description: String =
      "The number of connection timeouts that have occurred trying to obtain a connection from the pool"
    val unit: String = "{timeout}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[Counter[F, A]] =
      Meter[F]
        .counter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableCounter] =
      Meter[F]
        .observableCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** The time between borrowing a connection and returning it to the pool
    */
  object ClientConnectionUseTime extends MetricSpec {

    val name: String = "db.client.connection.use_time"
    val description: String = "The time between borrowing a connection and returning it to the pool"
    val unit: String = "s"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** The time it took to obtain an open connection from the pool
    */
  object ClientConnectionWaitTime extends MetricSpec {

    val name: String = "db.client.connection.wait_time"
    val description: String = "The time it took to obtain an open connection from the pool"
    val unit: String = "s"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of the connection pool; unique within the instrumented application. In case the connection pool
        * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make
        * the name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`,
        * formatted as `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name
        * following different patterns SHOULD document it.
        */
      val dbClientConnectionPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** Deprecated, use `db.client.connection.create_time` instead. Note: the unit also changed from `ms` to `s`.
    */
  @deprecated("Replaced by `db.client.connection.create_time`. Note: the unit also changed from `ms` to `s`.", "")
  object ClientConnectionsCreateTime extends MetricSpec {

    val name: String = "db.client.connections.create_time"
    val description: String =
      "Deprecated, use `db.client.connection.create_time` instead. Note: the unit also changed from `ms` to `s`."
    val unit: String = "ms"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** Deprecated, use `db.client.connection.idle.max` instead.
    */
  @deprecated("Replaced by `db.client.connection.idle.max`.", "")
  object ClientConnectionsIdleMax extends MetricSpec {

    val name: String = "db.client.connections.idle.max"
    val description: String = "Deprecated, use `db.client.connection.idle.max` instead."
    val unit: String = "{connection}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** Deprecated, use `db.client.connection.idle.min` instead.
    */
  @deprecated("Replaced by `db.client.connection.idle.min`.", "")
  object ClientConnectionsIdleMin extends MetricSpec {

    val name: String = "db.client.connections.idle.min"
    val description: String = "Deprecated, use `db.client.connection.idle.min` instead."
    val unit: String = "{connection}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** Deprecated, use `db.client.connection.max` instead.
    */
  @deprecated("Replaced by `db.client.connection.max`.", "")
  object ClientConnectionsMax extends MetricSpec {

    val name: String = "db.client.connections.max"
    val description: String = "Deprecated, use `db.client.connection.max` instead."
    val unit: String = "{connection}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** Deprecated, use `db.client.connection.pending_requests` instead.
    */
  @deprecated("Replaced by `db.client.connection.pending_requests`.", "")
  object ClientConnectionsPendingRequests extends MetricSpec {

    val name: String = "db.client.connections.pending_requests"
    val description: String = "Deprecated, use `db.client.connection.pending_requests` instead."
    val unit: String = "{request}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** Deprecated, use `db.client.connection.timeouts` instead.
    */
  @deprecated("Replaced by `db.client.connection.timeouts`.", "")
  object ClientConnectionsTimeouts extends MetricSpec {

    val name: String = "db.client.connections.timeouts"
    val description: String = "Deprecated, use `db.client.connection.timeouts` instead."
    val unit: String = "{timeout}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[Counter[F, A]] =
      Meter[F]
        .counter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableCounter] =
      Meter[F]
        .observableCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** Deprecated, use `db.client.connection.count` instead.
    */
  @deprecated("Replaced by `db.client.connection.count`.", "")
  object ClientConnectionsUsage extends MetricSpec {

    val name: String = "db.client.connections.usage"
    val description: String = "Deprecated, use `db.client.connection.count` instead."
    val unit: String = "{connection}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      /** Deprecated, use `db.client.connection.state` instead.
        */
      @deprecated("Replaced by `db.client.connection.state`.", "")
      val dbClientConnectionsState: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsState,
          List(
            "idle",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
          dbClientConnectionsState,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** Deprecated, use `db.client.connection.use_time` instead. Note: the unit also changed from `ms` to `s`.
    */
  @deprecated("Replaced by `db.client.connection.use_time`. Note: the unit also changed from `ms` to `s`.", "")
  object ClientConnectionsUseTime extends MetricSpec {

    val name: String = "db.client.connections.use_time"
    val description: String =
      "Deprecated, use `db.client.connection.use_time` instead. Note: the unit also changed from `ms` to `s`."
    val unit: String = "ms"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** Deprecated, use `db.client.connection.wait_time` instead. Note: the unit also changed from `ms` to `s`.
    */
  @deprecated("Replaced by `db.client.connection.wait_time`. Note: the unit also changed from `ms` to `s`.", "")
  object ClientConnectionsWaitTime extends MetricSpec {

    val name: String = "db.client.connections.wait_time"
    val description: String =
      "Deprecated, use `db.client.connection.wait_time` instead. Note: the unit also changed from `ms` to `s`."
    val unit: String = "ms"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Deprecated, use `db.client.connection.pool.name` instead.
        */
      @deprecated("Replaced by `db.client.connection.pool.name`.", "")
      val dbClientConnectionsPoolName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbClientConnectionsPoolName,
          List(
            "myDataSource",
          ),
          Requirement.required,
          Stability.development
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbClientConnectionsPoolName,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** Number of active client instances
    */
  object ClientCosmosdbActiveInstanceCount extends MetricSpec {

    val name: String = "db.client.cosmosdb.active_instance.count"
    val description: String = "Number of active client instances"
    val unit: String = "{instance}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Name of the database host. <p>
        * @note
        *   <p> When observed from the client side, and when communicating through an intermediary, `server.address`
        *   SHOULD represent the server address behind any intermediaries, for example proxies, if it's available.
        */
      val serverAddress: AttributeSpec[String] =
        AttributeSpec(
          ServerAttributes.ServerAddress,
          List(
            "example.com",
            "10.1.2.80",
            "/tmp/my.sock",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** Server port number. <p>
        * @note
        *   <p> When observed from the client side, and when communicating through an intermediary, `server.port` SHOULD
        *   represent the server port behind any intermediaries, for example proxies, if it's available.
        */
      val serverPort: AttributeSpec[Long] =
        AttributeSpec(
          ServerAttributes.ServerPort,
          List(
            80,
            8080,
            443,
          ),
          Requirement.conditionallyRequired(
            "If using a port other than the default port for this DBMS and if `server.address` is set."
          ),
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          serverAddress,
          serverPort,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue]: F[UpDownCounter[F, A]] =
      Meter[F]
        .upDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .create

    def createObserver[F[_]: Meter, A: MeasurementValue]: F[ObservableMeasurement[F, A]] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createObserver

    def createWithCallback[F[_]: Meter, A: MeasurementValue](
        callback: ObservableMeasurement[F, A] => F[Unit]
    ): Resource[F, ObservableUpDownCounter] =
      Meter[F]
        .observableUpDownCounter[A](name)
        .withDescription(description)
        .withUnit(unit)
        .createWithCallback(callback)

  }

  /** <a href="https://learn.microsoft.com/azure/cosmos-db/request-units">Request charge</a> consumed by the operation
    */
  object ClientCosmosdbOperationRequestCharge extends MetricSpec {

    val name: String = "db.client.cosmosdb.operation.request_charge"
    val description: String =
      "[Request charge](https://learn.microsoft.com/azure/cosmos-db/request-units) consumed by the operation"
    val unit: String = "{request_unit}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Cosmos DB container name. <p>
        * @note
        *   <p> It is RECOMMENDED to capture the value as provided by the application without attempting to do any case
        *   normalization. <p> The collection name SHOULD NOT be extracted from `db.query.text`, unless the query format
        *   is known to only ever have a single collection name present. <p> For batch operations, if the individual
        *   operations are known to have the same collection name then that collection name SHOULD be used. <p> This
        *   attribute has stability level RELEASE CANDIDATE.
        */
      val dbCollectionName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbCollectionName,
          List(
            "public.users",
            "customers",
          ),
          Requirement.conditionallyRequired("If available."),
          Stability.development
        )

      /** Account or request <a href="https://learn.microsoft.com/azure/cosmos-db/consistency-levels">consistency
        * level</a>.
        */
      val dbCosmosdbConsistencyLevel: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbCosmosdbConsistencyLevel,
          List(
            "Eventual",
            "ConsistentPrefix",
            "BoundedStaleness",
            "Strong",
            "Session",
          ),
          Requirement.conditionallyRequired("If available."),
          Stability.development
        )

      /** List of regions contacted during operation in the order that they were contacted. If there is more than one
        * region listed, it indicates that the operation was performed on multiple regions i.e. cross-regional call. <p>
        * @note
        *   <p> Region name matches the format of `displayName` in <a
        *   href="https://learn.microsoft.com/rest/api/subscription/subscriptions/list-locations?view=rest-subscription-2021-10-01&tabs=HTTP#location">Azure
        *   Location API</a>
        */
      val dbCosmosdbRegionsContacted: AttributeSpec[Seq[String]] =
        AttributeSpec(
          DbExperimentalAttributes.DbCosmosdbRegionsContacted,
          List(
            Seq("North Central US", "Australia East", "Australia Southeast"),
          ),
          Requirement.recommended("If available"),
          Stability.development
        )

      /** Cosmos DB sub status code.
        */
      val dbCosmosdbSubStatusCode: AttributeSpec[Long] =
        AttributeSpec(
          DbExperimentalAttributes.DbCosmosdbSubStatusCode,
          List(
            1000,
            1002,
          ),
          Requirement.conditionallyRequired("when response was received and contained sub-code."),
          Stability.development
        )

      /** The name of the database, fully qualified within the server address and port.
        */
      val dbNamespace: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbNamespace,
          List(
            "customers",
            "test.users",
          ),
          Requirement.conditionallyRequired("If available."),
          Stability.development
        )

      /** The name of the operation or command being executed. <p>
        * @note
        *   <p> It is RECOMMENDED to capture the value as provided by the application without attempting to do any case
        *   normalization. <p> The operation name SHOULD NOT be extracted from `db.query.text`, unless the query format
        *   is known to only ever have a single operation name present. <p> For batch operations, if the individual
        *   operations are known to have the same operation name then that operation name SHOULD be used prepended by
        *   `BATCH `, otherwise `db.operation.name` SHOULD be `BATCH` or some other database system specific term if
        *   more applicable. <p> This attribute has stability level RELEASE CANDIDATE.
        */
      val dbOperationName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbOperationName,
          List(
            "findAndModify",
            "HMSET",
            "SELECT",
          ),
          Requirement.conditionallyRequired(
            "If readily available and if there is a single operation name that describes the database call. The operation name MAY be parsed from the query text, in which case it SHOULD be the single operation name found in the query."
          ),
          Stability.development
        )

      /** Database response status code. <p>
        * @note
        *   <p> The status code returned by the database. Usually it represents an error code, but may also represent
        *   partial success, warning, or differentiate between various types of successful outcomes. Semantic
        *   conventions for individual database systems SHOULD document what `db.response.status_code` means in the
        *   context of that system. This attribute has stability level RELEASE CANDIDATE.
        */
      val dbResponseStatusCode: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbResponseStatusCode,
          List(
            "102",
            "ORA-17002",
            "08P01",
            "404",
          ),
          Requirement.conditionallyRequired("If the operation failed and status code is available."),
          Stability.development
        )

      /** Describes a class of error the operation ended with. <p>
        * @note
        *   <p> The `error.type` SHOULD match the `db.response.status_code` returned by the database or the client
        *   library, or the canonical name of exception that occurred. When using canonical exception type name,
        *   instrumentation SHOULD do the best effort to report the most relevant type. For example, if the original
        *   exception is wrapped into a generic one, the original exception SHOULD be preferred. Instrumentations SHOULD
        *   document how `error.type` is populated.
        */
      val errorType: AttributeSpec[String] =
        AttributeSpec(
          ErrorAttributes.ErrorType,
          List(
            "timeout",
            "java.net.UnknownHostException",
            "server_certificate_invalid",
            "500",
          ),
          Requirement.conditionallyRequired("If and only if the operation failed."),
          Stability.stable
        )

      /** Name of the database host. <p>
        * @note
        *   <p> When observed from the client side, and when communicating through an intermediary, `server.address`
        *   SHOULD represent the server address behind any intermediaries, for example proxies, if it's available.
        */
      val serverAddress: AttributeSpec[String] =
        AttributeSpec(
          ServerAttributes.ServerAddress,
          List(
            "example.com",
            "10.1.2.80",
            "/tmp/my.sock",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** Server port number. <p>
        * @note
        *   <p> When observed from the client side, and when communicating through an intermediary, `server.port` SHOULD
        *   represent the server port behind any intermediaries, for example proxies, if it's available.
        */
      val serverPort: AttributeSpec[Long] =
        AttributeSpec(
          ServerAttributes.ServerPort,
          List(
            80,
            8080,
            443,
          ),
          Requirement.conditionallyRequired(
            "If using a port other than the default port for this DBMS and if `server.address` is set."
          ),
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbCollectionName,
          dbCosmosdbConsistencyLevel,
          dbCosmosdbRegionsContacted,
          dbCosmosdbSubStatusCode,
          dbNamespace,
          dbOperationName,
          dbResponseStatusCode,
          errorType,
          serverAddress,
          serverPort,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** Duration of database client operations. <p>
    * @note
    *   <p> Batch operations SHOULD be recorded as a single operation.
    */
  object ClientOperationDuration extends MetricSpec {

    val name: String = "db.client.operation.duration"
    val description: String = "Duration of database client operations."
    val unit: String = "s"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of a collection (table, container) within the database. <p>
        * @note
        *   <p> It is RECOMMENDED to capture the value as provided by the application without attempting to do any case
        *   normalization. <p> The collection name SHOULD NOT be extracted from `db.query.text`, unless the query format
        *   is known to only ever have a single collection name present. <p> For batch operations, if the individual
        *   operations are known to have the same collection name then that collection name SHOULD be used. <p> This
        *   attribute has stability level RELEASE CANDIDATE.
        */
      val dbCollectionName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbCollectionName,
          List(
            "public.users",
            "customers",
          ),
          Requirement.conditionallyRequired(
            "If readily available and if a database call is performed on a single collection. The collection name MAY be parsed from the query text, in which case it SHOULD be the single collection name in the query."
          ),
          Stability.development
        )

      /** The name of the database, fully qualified within the server address and port. <p>
        * @note
        *   <p> If a database system has multiple namespace components, they SHOULD be concatenated (potentially using
        *   database system specific conventions) from most general to most specific namespace component, and more
        *   specific namespaces SHOULD NOT be captured without the more general namespaces, to ensure that "startswith"
        *   queries for the more general namespaces will be valid. Semantic conventions for individual database systems
        *   SHOULD document what `db.namespace` means in the context of that system. It is RECOMMENDED to capture the
        *   value as provided by the application without attempting to do any case normalization. This attribute has
        *   stability level RELEASE CANDIDATE.
        */
      val dbNamespace: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbNamespace,
          List(
            "customers",
            "test.users",
          ),
          Requirement.conditionallyRequired("If available."),
          Stability.development
        )

      /** The name of the operation or command being executed. <p>
        * @note
        *   <p> It is RECOMMENDED to capture the value as provided by the application without attempting to do any case
        *   normalization. <p> The operation name SHOULD NOT be extracted from `db.query.text`, unless the query format
        *   is known to only ever have a single operation name present. <p> For batch operations, if the individual
        *   operations are known to have the same operation name then that operation name SHOULD be used prepended by
        *   `BATCH `, otherwise `db.operation.name` SHOULD be `BATCH` or some other database system specific term if
        *   more applicable. <p> This attribute has stability level RELEASE CANDIDATE.
        */
      val dbOperationName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbOperationName,
          List(
            "findAndModify",
            "HMSET",
            "SELECT",
          ),
          Requirement.conditionallyRequired(
            "If readily available and if there is a single operation name that describes the database call. The operation name MAY be parsed from the query text, in which case it SHOULD be the single operation name found in the query."
          ),
          Stability.development
        )

      /** Low cardinality representation of a database query text. <p>
        * @note
        *   <p> `db.query.summary` provides static summary of the query text. It describes a class of database queries
        *   and is useful as a grouping key, especially when analyzing telemetry for database calls involving complex
        *   queries. Summary may be available to the instrumentation through instrumentation hooks or other means. If it
        *   is not available, instrumentations that support query parsing SHOULD generate a summary following <a
        *   href="../../docs/database/database-spans.md#generating-a-summary-of-the-query-text">Generating query
        *   summary</a> section. This attribute has stability level RELEASE CANDIDATE.
        */
      val dbQuerySummary: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbQuerySummary,
          List(
            "SELECT wuser_table",
            "INSERT shipping_details SELECT orders",
            "get user by id",
          ),
          Requirement.recommended("if readily available or if instrumentation supports query summarization."),
          Stability.development
        )

      /** The database query being executed. <p>
        * @note
        *   <p> For sanitization see <a
        *   href="../../docs/database/database-spans.md#sanitization-of-dbquerytext">Sanitization of `db.query.text`
        *   </a>. For batch operations, if the individual operations are known to have the same query text then that
        *   query text SHOULD be used, otherwise all of the individual query texts SHOULD be concatenated with separator
        *   `; ` or some other database system specific separator if more applicable. Even though parameterized query
        *   text can potentially have sensitive data, by using a parameterized query the user is giving a strong signal
        *   that any sensitive data will be passed as parameter values, and the benefit to observability of capturing
        *   the static part of the query text by default outweighs the risk. This attribute has stability level RELEASE
        *   CANDIDATE.
        */
      val dbQueryText: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbQueryText,
          List(
            "SELECT * FROM wuser_table where username = ?",
            "SET mykey ?",
          ),
          Requirement.optIn,
          Stability.development
        )

      /** Database response status code. <p>
        * @note
        *   <p> The status code returned by the database. Usually it represents an error code, but may also represent
        *   partial success, warning, or differentiate between various types of successful outcomes. Semantic
        *   conventions for individual database systems SHOULD document what `db.response.status_code` means in the
        *   context of that system. This attribute has stability level RELEASE CANDIDATE.
        */
      val dbResponseStatusCode: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbResponseStatusCode,
          List(
            "102",
            "ORA-17002",
            "08P01",
            "404",
          ),
          Requirement.conditionallyRequired("If the operation failed and status code is available."),
          Stability.development
        )

      /** The database management system (DBMS) product as identified by the client instrumentation. <p>
        * @note
        *   <p> The actual DBMS may differ from the one identified by the client. For example, when using PostgreSQL
        *   client libraries to connect to a CockroachDB, the `db.system` is set to `postgresql` based on the
        *   instrumentation's best knowledge. This attribute has stability level RELEASE CANDIDATE.
        */
      val dbSystem: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbSystem,
          List(
          ),
          Requirement.required,
          Stability.development
        )

      /** Describes a class of error the operation ended with. <p>
        * @note
        *   <p> The `error.type` SHOULD match the `db.response.status_code` returned by the database or the client
        *   library, or the canonical name of exception that occurred. When using canonical exception type name,
        *   instrumentation SHOULD do the best effort to report the most relevant type. For example, if the original
        *   exception is wrapped into a generic one, the original exception SHOULD be preferred. Instrumentations SHOULD
        *   document how `error.type` is populated.
        */
      val errorType: AttributeSpec[String] =
        AttributeSpec(
          ErrorAttributes.ErrorType,
          List(
            "timeout",
            "java.net.UnknownHostException",
            "server_certificate_invalid",
            "500",
          ),
          Requirement.conditionallyRequired("If and only if the operation failed."),
          Stability.stable
        )

      /** Peer address of the database node where the operation was performed. <p>
        * @note
        *   <p> Semantic conventions for individual database systems SHOULD document whether `network.peer.*` attributes
        *   are applicable. Network peer address and port are useful when the application interacts with individual
        *   database nodes directly. If a database operation involved multiple network calls (for example retries), the
        *   address of the last contacted node SHOULD be used.
        */
      val networkPeerAddress: AttributeSpec[String] =
        AttributeSpec(
          NetworkAttributes.NetworkPeerAddress,
          List(
            "10.1.2.80",
            "/tmp/my.sock",
          ),
          Requirement.recommended("If applicable for this database system."),
          Stability.stable
        )

      /** Peer port number of the network connection.
        */
      val networkPeerPort: AttributeSpec[Long] =
        AttributeSpec(
          NetworkAttributes.NetworkPeerPort,
          List(
            65123,
          ),
          Requirement.recommended("If and only if `network.peer.address` is set."),
          Stability.stable
        )

      /** Name of the database host. <p>
        * @note
        *   <p> When observed from the client side, and when communicating through an intermediary, `server.address`
        *   SHOULD represent the server address behind any intermediaries, for example proxies, if it's available.
        */
      val serverAddress: AttributeSpec[String] =
        AttributeSpec(
          ServerAttributes.ServerAddress,
          List(
            "example.com",
            "10.1.2.80",
            "/tmp/my.sock",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** Server port number. <p>
        * @note
        *   <p> When observed from the client side, and when communicating through an intermediary, `server.port` SHOULD
        *   represent the server port behind any intermediaries, for example proxies, if it's available.
        */
      val serverPort: AttributeSpec[Long] =
        AttributeSpec(
          ServerAttributes.ServerPort,
          List(
            80,
            8080,
            443,
          ),
          Requirement.conditionallyRequired(
            "If using a port other than the default port for this DBMS and if `server.address` is set."
          ),
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbCollectionName,
          dbNamespace,
          dbOperationName,
          dbQuerySummary,
          dbQueryText,
          dbResponseStatusCode,
          dbSystem,
          errorType,
          networkPeerAddress,
          networkPeerPort,
          serverAddress,
          serverPort,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** The actual number of records returned by the database operation.
    */
  object ClientResponseReturnedRows extends MetricSpec {

    val name: String = "db.client.response.returned_rows"
    val description: String = "The actual number of records returned by the database operation."
    val unit: String = "{row}"
    val stability: Stability = Stability.development
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** The name of a collection (table, container) within the database. <p>
        * @note
        *   <p> It is RECOMMENDED to capture the value as provided by the application without attempting to do any case
        *   normalization. <p> The collection name SHOULD NOT be extracted from `db.query.text`, unless the query format
        *   is known to only ever have a single collection name present. <p> For batch operations, if the individual
        *   operations are known to have the same collection name then that collection name SHOULD be used. <p> This
        *   attribute has stability level RELEASE CANDIDATE.
        */
      val dbCollectionName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbCollectionName,
          List(
            "public.users",
            "customers",
          ),
          Requirement.conditionallyRequired(
            "If readily available and if a database call is performed on a single collection. The collection name MAY be parsed from the query text, in which case it SHOULD be the single collection name in the query."
          ),
          Stability.development
        )

      /** The name of the database, fully qualified within the server address and port. <p>
        * @note
        *   <p> If a database system has multiple namespace components, they SHOULD be concatenated (potentially using
        *   database system specific conventions) from most general to most specific namespace component, and more
        *   specific namespaces SHOULD NOT be captured without the more general namespaces, to ensure that "startswith"
        *   queries for the more general namespaces will be valid. Semantic conventions for individual database systems
        *   SHOULD document what `db.namespace` means in the context of that system. It is RECOMMENDED to capture the
        *   value as provided by the application without attempting to do any case normalization. This attribute has
        *   stability level RELEASE CANDIDATE.
        */
      val dbNamespace: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbNamespace,
          List(
            "customers",
            "test.users",
          ),
          Requirement.conditionallyRequired("If available."),
          Stability.development
        )

      /** The name of the operation or command being executed. <p>
        * @note
        *   <p> It is RECOMMENDED to capture the value as provided by the application without attempting to do any case
        *   normalization. <p> The operation name SHOULD NOT be extracted from `db.query.text`, unless the query format
        *   is known to only ever have a single operation name present. <p> For batch operations, if the individual
        *   operations are known to have the same operation name then that operation name SHOULD be used prepended by
        *   `BATCH `, otherwise `db.operation.name` SHOULD be `BATCH` or some other database system specific term if
        *   more applicable. <p> This attribute has stability level RELEASE CANDIDATE.
        */
      val dbOperationName: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbOperationName,
          List(
            "findAndModify",
            "HMSET",
            "SELECT",
          ),
          Requirement.conditionallyRequired(
            "If readily available and if there is a single operation name that describes the database call. The operation name MAY be parsed from the query text, in which case it SHOULD be the single operation name found in the query."
          ),
          Stability.development
        )

      /** Low cardinality representation of a database query text. <p>
        * @note
        *   <p> `db.query.summary` provides static summary of the query text. It describes a class of database queries
        *   and is useful as a grouping key, especially when analyzing telemetry for database calls involving complex
        *   queries. Summary may be available to the instrumentation through instrumentation hooks or other means. If it
        *   is not available, instrumentations that support query parsing SHOULD generate a summary following <a
        *   href="../../docs/database/database-spans.md#generating-a-summary-of-the-query-text">Generating query
        *   summary</a> section. This attribute has stability level RELEASE CANDIDATE.
        */
      val dbQuerySummary: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbQuerySummary,
          List(
            "SELECT wuser_table",
            "INSERT shipping_details SELECT orders",
            "get user by id",
          ),
          Requirement.recommended("if readily available or if instrumentation supports query summarization."),
          Stability.development
        )

      /** The database query being executed. <p>
        * @note
        *   <p> For sanitization see <a
        *   href="../../docs/database/database-spans.md#sanitization-of-dbquerytext">Sanitization of `db.query.text`
        *   </a>. For batch operations, if the individual operations are known to have the same query text then that
        *   query text SHOULD be used, otherwise all of the individual query texts SHOULD be concatenated with separator
        *   `; ` or some other database system specific separator if more applicable. Even though parameterized query
        *   text can potentially have sensitive data, by using a parameterized query the user is giving a strong signal
        *   that any sensitive data will be passed as parameter values, and the benefit to observability of capturing
        *   the static part of the query text by default outweighs the risk. This attribute has stability level RELEASE
        *   CANDIDATE.
        */
      val dbQueryText: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbQueryText,
          List(
            "SELECT * FROM wuser_table where username = ?",
            "SET mykey ?",
          ),
          Requirement.optIn,
          Stability.development
        )

      /** Database response status code. <p>
        * @note
        *   <p> The status code returned by the database. Usually it represents an error code, but may also represent
        *   partial success, warning, or differentiate between various types of successful outcomes. Semantic
        *   conventions for individual database systems SHOULD document what `db.response.status_code` means in the
        *   context of that system. This attribute has stability level RELEASE CANDIDATE.
        */
      val dbResponseStatusCode: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbResponseStatusCode,
          List(
            "102",
            "ORA-17002",
            "08P01",
            "404",
          ),
          Requirement.conditionallyRequired("If the operation failed and status code is available."),
          Stability.development
        )

      /** The database management system (DBMS) product as identified by the client instrumentation. <p>
        * @note
        *   <p> The actual DBMS may differ from the one identified by the client. For example, when using PostgreSQL
        *   client libraries to connect to a CockroachDB, the `db.system` is set to `postgresql` based on the
        *   instrumentation's best knowledge. This attribute has stability level RELEASE CANDIDATE.
        */
      val dbSystem: AttributeSpec[String] =
        AttributeSpec(
          DbExperimentalAttributes.DbSystem,
          List(
          ),
          Requirement.required,
          Stability.development
        )

      /** Describes a class of error the operation ended with. <p>
        * @note
        *   <p> The `error.type` SHOULD match the `db.response.status_code` returned by the database or the client
        *   library, or the canonical name of exception that occurred. When using canonical exception type name,
        *   instrumentation SHOULD do the best effort to report the most relevant type. For example, if the original
        *   exception is wrapped into a generic one, the original exception SHOULD be preferred. Instrumentations SHOULD
        *   document how `error.type` is populated.
        */
      val errorType: AttributeSpec[String] =
        AttributeSpec(
          ErrorAttributes.ErrorType,
          List(
            "timeout",
            "java.net.UnknownHostException",
            "server_certificate_invalid",
            "500",
          ),
          Requirement.conditionallyRequired("If and only if the operation failed."),
          Stability.stable
        )

      /** Peer address of the database node where the operation was performed. <p>
        * @note
        *   <p> Semantic conventions for individual database systems SHOULD document whether `network.peer.*` attributes
        *   are applicable. Network peer address and port are useful when the application interacts with individual
        *   database nodes directly. If a database operation involved multiple network calls (for example retries), the
        *   address of the last contacted node SHOULD be used.
        */
      val networkPeerAddress: AttributeSpec[String] =
        AttributeSpec(
          NetworkAttributes.NetworkPeerAddress,
          List(
            "10.1.2.80",
            "/tmp/my.sock",
          ),
          Requirement.recommended("If applicable for this database system."),
          Stability.stable
        )

      /** Peer port number of the network connection.
        */
      val networkPeerPort: AttributeSpec[Long] =
        AttributeSpec(
          NetworkAttributes.NetworkPeerPort,
          List(
            65123,
          ),
          Requirement.recommended("If and only if `network.peer.address` is set."),
          Stability.stable
        )

      /** Name of the database host. <p>
        * @note
        *   <p> When observed from the client side, and when communicating through an intermediary, `server.address`
        *   SHOULD represent the server address behind any intermediaries, for example proxies, if it's available.
        */
      val serverAddress: AttributeSpec[String] =
        AttributeSpec(
          ServerAttributes.ServerAddress,
          List(
            "example.com",
            "10.1.2.80",
            "/tmp/my.sock",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** Server port number. <p>
        * @note
        *   <p> When observed from the client side, and when communicating through an intermediary, `server.port` SHOULD
        *   represent the server port behind any intermediaries, for example proxies, if it's available.
        */
      val serverPort: AttributeSpec[Long] =
        AttributeSpec(
          ServerAttributes.ServerPort,
          List(
            80,
            8080,
            443,
          ),
          Requirement.conditionallyRequired(
            "If using a port other than the default port for this DBMS and if `server.address` is set."
          ),
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          dbCollectionName,
          dbNamespace,
          dbOperationName,
          dbQuerySummary,
          dbQueryText,
          dbResponseStatusCode,
          dbSystem,
          errorType,
          networkPeerAddress,
          networkPeerPort,
          serverAddress,
          serverPort,
        )
    }

    def create[F[_]: Meter, A: MeasurementValue](boundaries: BucketBoundaries): F[Histogram[F, A]] =
      Meter[F]
        .histogram[A](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

}
