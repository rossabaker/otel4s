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
package semconv
package experimental.attributes

// DO NOT EDIT, this is an Auto-generated file from buildscripts/templates/registry/otel4s/attributes/SemanticAttributes.scala.j2
object DbExperimentalAttributes {

  /** The consistency level of the query. Based on consistency values from <a
    * href="https://docs.datastax.com/en/cassandra-oss/3.0/cassandra/dml/dmlConfigConsistency.html">CQL</a>.
    */
  val DbCassandraConsistencyLevel: AttributeKey[String] =
    AttributeKey("db.cassandra.consistency_level")

  /** The data center of the coordinating node for a query.
    */
  val DbCassandraCoordinatorDc: AttributeKey[String] =
    AttributeKey("db.cassandra.coordinator.dc")

  /** The ID of the coordinating node for a query.
    */
  val DbCassandraCoordinatorId: AttributeKey[String] =
    AttributeKey("db.cassandra.coordinator.id")

  /** Whether or not the query is idempotent.
    */
  val DbCassandraIdempotence: AttributeKey[Boolean] =
    AttributeKey("db.cassandra.idempotence")

  /** The fetch size used for paging, i.e. how many rows will be returned at once.
    */
  val DbCassandraPageSize: AttributeKey[Long] =
    AttributeKey("db.cassandra.page_size")

  /** The number of times a query was speculatively executed. Not set or `0` if the query was not executed
    * speculatively.
    */
  val DbCassandraSpeculativeExecutionCount: AttributeKey[Long] =
    AttributeKey("db.cassandra.speculative_execution_count")

  /** Deprecated, use `db.collection.name` instead.
    */
  @deprecated("Replaced by `db.collection.name`.", "")
  val DbCassandraTable: AttributeKey[String] =
    AttributeKey("db.cassandra.table")

  /** The name of the connection pool; unique within the instrumented application. In case the connection pool
    * implementation doesn't provide a name, instrumentation SHOULD use a combination of parameters that would make the
    * name unique, for example, combining attributes `server.address`, `server.port`, and `db.namespace`, formatted as
    * `server.address:server.port/db.namespace`. Instrumentations that generate connection pool name following different
    * patterns SHOULD document it.
    */
  val DbClientConnectionPoolName: AttributeKey[String] =
    AttributeKey("db.client.connection.pool.name")

  /** The state of a connection in the pool
    */
  val DbClientConnectionState: AttributeKey[String] =
    AttributeKey("db.client.connection.state")

  /** Deprecated, use `db.client.connection.pool.name` instead.
    */
  @deprecated("Replaced by `db.client.connection.pool.name`.", "")
  val DbClientConnectionsPoolName: AttributeKey[String] =
    AttributeKey("db.client.connections.pool.name")

  /** Deprecated, use `db.client.connection.state` instead.
    */
  @deprecated("Replaced by `db.client.connection.state`.", "")
  val DbClientConnectionsState: AttributeKey[String] =
    AttributeKey("db.client.connections.state")

  /** The name of a collection (table, container) within the database. <p>
    * @note
    *   <p> It is RECOMMENDED to capture the value as provided by the application without attempting to do any case
    *   normalization. <p> The collection name SHOULD NOT be extracted from `db.query.text`, unless the query format is
    *   known to only ever have a single collection name present. <p> For batch operations, if the individual operations
    *   are known to have the same collection name then that collection name SHOULD be used. <p> This attribute has
    *   stability level RELEASE CANDIDATE.
    */
  val DbCollectionName: AttributeKey[String] =
    AttributeKey("db.collection.name")

  /** Deprecated, use `server.address`, `server.port` attributes instead.
    */
  @deprecated("Replaced by `server.address` and `server.port`.", "")
  val DbConnectionString: AttributeKey[String] =
    AttributeKey("db.connection_string")

  /** Unique Cosmos client instance id.
    */
  val DbCosmosdbClientId: AttributeKey[String] =
    AttributeKey("db.cosmosdb.client_id")

  /** Cosmos client connection mode.
    */
  val DbCosmosdbConnectionMode: AttributeKey[String] =
    AttributeKey("db.cosmosdb.connection_mode")

  /** Account or request <a href="https://learn.microsoft.com/azure/cosmos-db/consistency-levels">consistency level</a>.
    */
  val DbCosmosdbConsistencyLevel: AttributeKey[String] =
    AttributeKey("db.cosmosdb.consistency_level")

  /** Deprecated, use `db.collection.name` instead.
    */
  @deprecated("Replaced by `db.collection.name`.", "")
  val DbCosmosdbContainer: AttributeKey[String] =
    AttributeKey("db.cosmosdb.container")

  /** Deprecated, no replacement at this time.
    */
  @deprecated("No replacement at this time.", "")
  val DbCosmosdbOperationType: AttributeKey[String] =
    AttributeKey("db.cosmosdb.operation_type")

  /** List of regions contacted during operation in the order that they were contacted. If there is more than one region
    * listed, it indicates that the operation was performed on multiple regions i.e. cross-regional call. <p>
    * @note
    *   <p> Region name matches the format of `displayName` in <a
    *   href="https://learn.microsoft.com/rest/api/subscription/subscriptions/list-locations?view=rest-subscription-2021-10-01&tabs=HTTP#location">Azure
    *   Location API</a>
    */
  val DbCosmosdbRegionsContacted: AttributeKey[Seq[String]] =
    AttributeKey("db.cosmosdb.regions_contacted")

  /** Request units consumed for the operation.
    */
  val DbCosmosdbRequestCharge: AttributeKey[Double] =
    AttributeKey("db.cosmosdb.request_charge")

  /** Request payload size in bytes.
    */
  val DbCosmosdbRequestContentLength: AttributeKey[Long] =
    AttributeKey("db.cosmosdb.request_content_length")

  /** Deprecated, use `db.response.status_code` instead.
    */
  @deprecated("Replaced by `db.response.status_code`.", "")
  val DbCosmosdbStatusCode: AttributeKey[Long] =
    AttributeKey("db.cosmosdb.status_code")

  /** Cosmos DB sub status code.
    */
  val DbCosmosdbSubStatusCode: AttributeKey[Long] =
    AttributeKey("db.cosmosdb.sub_status_code")

  /** Deprecated, use `db.namespace` instead.
    */
  @deprecated("Replaced by `db.namespace`.", "")
  val DbElasticsearchClusterName: AttributeKey[String] =
    AttributeKey("db.elasticsearch.cluster.name")

  /** Represents the human-readable identifier of the node/instance to which a request was routed.
    */
  val DbElasticsearchNodeName: AttributeKey[String] =
    AttributeKey("db.elasticsearch.node.name")

  /** A dynamic value in the url path. <p>
    * @note
    *   <p> Many Elasticsearch url paths allow dynamic values. These SHOULD be recorded in span attributes in the format
    *   `db.elasticsearch.path_parts.<key>`, where `<key>` is the url path part name. The implementation SHOULD
    *   reference the <a
    *   href="https://raw.githubusercontent.com/elastic/elasticsearch-specification/main/output/schema/schema.json">elasticsearch
    *   schema</a> in order to map the path part values to their names.
    */
  val DbElasticsearchPathParts: AttributeKey[String] =
    AttributeKey("db.elasticsearch.path_parts")

  /** Deprecated, no general replacement at this time. For Elasticsearch, use `db.elasticsearch.node.name` instead.
    */
  @deprecated(
    "Deprecated, no general replacement at this time. For Elasticsearch, use `db.elasticsearch.node.name` instead.",
    ""
  )
  val DbInstanceId: AttributeKey[String] =
    AttributeKey("db.instance.id")

  /** Removed, no replacement at this time.
    */
  @deprecated("Removed as not used.", "")
  val DbJdbcDriverClassname: AttributeKey[String] =
    AttributeKey("db.jdbc.driver_classname")

  /** Deprecated, use `db.collection.name` instead.
    */
  @deprecated("Replaced by `db.collection.name`.", "")
  val DbMongodbCollection: AttributeKey[String] =
    AttributeKey("db.mongodb.collection")

  /** Deprecated, SQL Server instance is now populated as a part of `db.namespace` attribute.
    */
  @deprecated("Deprecated, no replacement at this time.", "")
  val DbMssqlInstanceName: AttributeKey[String] =
    AttributeKey("db.mssql.instance_name")

  /** Deprecated, use `db.namespace` instead.
    */
  @deprecated("Replaced by `db.namespace`.", "")
  val DbName: AttributeKey[String] =
    AttributeKey("db.name")

  /** The name of the database, fully qualified within the server address and port. <p>
    * @note
    *   <p> If a database system has multiple namespace components, they SHOULD be concatenated (potentially using
    *   database system specific conventions) from most general to most specific namespace component, and more specific
    *   namespaces SHOULD NOT be captured without the more general namespaces, to ensure that "startswith" queries for
    *   the more general namespaces will be valid. Semantic conventions for individual database systems SHOULD document
    *   what `db.namespace` means in the context of that system. It is RECOMMENDED to capture the value as provided by
    *   the application without attempting to do any case normalization. This attribute has stability level RELEASE
    *   CANDIDATE.
    */
  val DbNamespace: AttributeKey[String] =
    AttributeKey("db.namespace")

  /** Deprecated, use `db.operation.name` instead.
    */
  @deprecated("Replaced by `db.operation.name`.", "")
  val DbOperation: AttributeKey[String] =
    AttributeKey("db.operation")

  /** The number of queries included in a batch operation. <p>
    * @note
    *   <p> Operations are only considered batches when they contain two or more operations, and so
    *   `db.operation.batch.size` SHOULD never be `1`. This attribute has stability level RELEASE CANDIDATE.
    */
  val DbOperationBatchSize: AttributeKey[Long] =
    AttributeKey("db.operation.batch.size")

  /** The name of the operation or command being executed. <p>
    * @note
    *   <p> It is RECOMMENDED to capture the value as provided by the application without attempting to do any case
    *   normalization. <p> The operation name SHOULD NOT be extracted from `db.query.text`, unless the query format is
    *   known to only ever have a single operation name present. <p> For batch operations, if the individual operations
    *   are known to have the same operation name then that operation name SHOULD be used prepended by `BATCH `,
    *   otherwise `db.operation.name` SHOULD be `BATCH` or some other database system specific term if more applicable.
    *   <p> This attribute has stability level RELEASE CANDIDATE.
    */
  val DbOperationName: AttributeKey[String] =
    AttributeKey("db.operation.name")

  /** A database operation parameter, with `<key>` being the parameter name, and the attribute value being a string
    * representation of the parameter value. <p>
    * @note
    *   <p> If a parameter has no name and instead is referenced only by index, then `<key>` SHOULD be the 0-based
    *   index. If `db.query.text` is also captured, then `db.operation.parameter.<key>` SHOULD match up with the
    *   parameterized placeholders present in `db.query.text`. This attribute has stability level RELEASE CANDIDATE.
    */
  val DbOperationParameter: AttributeKey[String] =
    AttributeKey("db.operation.parameter")

  /** A query parameter used in `db.query.text`, with `<key>` being the parameter name, and the attribute value being a
    * string representation of the parameter value.
    */
  @deprecated("Replaced by `db.operation.parameter`.", "")
  val DbQueryParameter: AttributeKey[String] =
    AttributeKey("db.query.parameter")

  /** Low cardinality representation of a database query text. <p>
    * @note
    *   <p> `db.query.summary` provides static summary of the query text. It describes a class of database queries and
    *   is useful as a grouping key, especially when analyzing telemetry for database calls involving complex queries.
    *   Summary may be available to the instrumentation through instrumentation hooks or other means. If it is not
    *   available, instrumentations that support query parsing SHOULD generate a summary following <a
    *   href="../../docs/database/database-spans.md#generating-a-summary-of-the-query-text">Generating query summary</a>
    *   section. This attribute has stability level RELEASE CANDIDATE.
    */
  val DbQuerySummary: AttributeKey[String] =
    AttributeKey("db.query.summary")

  /** The database query being executed. <p>
    * @note
    *   <p> For sanitization see <a
    *   href="../../docs/database/database-spans.md#sanitization-of-dbquerytext">Sanitization of `db.query.text` </a>.
    *   For batch operations, if the individual operations are known to have the same query text then that query text
    *   SHOULD be used, otherwise all of the individual query texts SHOULD be concatenated with separator `; ` or some
    *   other database system specific separator if more applicable. Even though parameterized query text can
    *   potentially have sensitive data, by using a parameterized query the user is giving a strong signal that any
    *   sensitive data will be passed as parameter values, and the benefit to observability of capturing the static part
    *   of the query text by default outweighs the risk. This attribute has stability level RELEASE CANDIDATE.
    */
  val DbQueryText: AttributeKey[String] =
    AttributeKey("db.query.text")

  /** Deprecated, use `db.namespace` instead.
    */
  @deprecated("Replaced by `db.namespace`.", "")
  val DbRedisDatabaseIndex: AttributeKey[Long] =
    AttributeKey("db.redis.database_index")

  /** Number of rows returned by the operation.
    */
  val DbResponseReturnedRows: AttributeKey[Long] =
    AttributeKey("db.response.returned_rows")

  /** Database response status code. <p>
    * @note
    *   <p> The status code returned by the database. Usually it represents an error code, but may also represent
    *   partial success, warning, or differentiate between various types of successful outcomes. Semantic conventions
    *   for individual database systems SHOULD document what `db.response.status_code` means in the context of that
    *   system. This attribute has stability level RELEASE CANDIDATE.
    */
  val DbResponseStatusCode: AttributeKey[String] =
    AttributeKey("db.response.status_code")

  /** Deprecated, use `db.collection.name` instead.
    */
  @deprecated("Replaced by `db.collection.name`.", "")
  val DbSqlTable: AttributeKey[String] =
    AttributeKey("db.sql.table")

  /** The database statement being executed.
    */
  @deprecated("Replaced by `db.query.text`.", "")
  val DbStatement: AttributeKey[String] =
    AttributeKey("db.statement")

  /** The database management system (DBMS) product as identified by the client instrumentation. <p>
    * @note
    *   <p> The actual DBMS may differ from the one identified by the client. For example, when using PostgreSQL client
    *   libraries to connect to a CockroachDB, the `db.system` is set to `postgresql` based on the instrumentation's
    *   best knowledge. This attribute has stability level RELEASE CANDIDATE.
    */
  val DbSystem: AttributeKey[String] =
    AttributeKey("db.system")

  /** Deprecated, no replacement at this time.
    */
  @deprecated("No replacement at this time.", "")
  val DbUser: AttributeKey[String] =
    AttributeKey("db.user")

  /** Values for [[DbCassandraConsistencyLevel]].
    */
  abstract class DbCassandraConsistencyLevelValue(val value: String)
  object DbCassandraConsistencyLevelValue {

    /** all.
      */
    case object All extends DbCassandraConsistencyLevelValue("all")

    /** each_quorum.
      */
    case object EachQuorum extends DbCassandraConsistencyLevelValue("each_quorum")

    /** quorum.
      */
    case object Quorum extends DbCassandraConsistencyLevelValue("quorum")

    /** local_quorum.
      */
    case object LocalQuorum extends DbCassandraConsistencyLevelValue("local_quorum")

    /** one.
      */
    case object One extends DbCassandraConsistencyLevelValue("one")

    /** two.
      */
    case object Two extends DbCassandraConsistencyLevelValue("two")

    /** three.
      */
    case object Three extends DbCassandraConsistencyLevelValue("three")

    /** local_one.
      */
    case object LocalOne extends DbCassandraConsistencyLevelValue("local_one")

    /** any.
      */
    case object Any extends DbCassandraConsistencyLevelValue("any")

    /** serial.
      */
    case object Serial extends DbCassandraConsistencyLevelValue("serial")

    /** local_serial.
      */
    case object LocalSerial extends DbCassandraConsistencyLevelValue("local_serial")
  }

  /** Values for [[DbClientConnectionState]].
    */
  abstract class DbClientConnectionStateValue(val value: String)
  object DbClientConnectionStateValue {

    /** idle.
      */
    case object Idle extends DbClientConnectionStateValue("idle")

    /** used.
      */
    case object Used extends DbClientConnectionStateValue("used")
  }

  /** Values for [[DbClientConnectionsState]].
    */
  @deprecated("Replaced by `db.client.connection.state`.", "")
  abstract class DbClientConnectionsStateValue(val value: String)
  @annotation.nowarn("cat=deprecation")
  object DbClientConnectionsStateValue {

    /** idle.
      */
    case object Idle extends DbClientConnectionsStateValue("idle")

    /** used.
      */
    case object Used extends DbClientConnectionsStateValue("used")
  }

  /** Values for [[DbCosmosdbConnectionMode]].
    */
  abstract class DbCosmosdbConnectionModeValue(val value: String)
  object DbCosmosdbConnectionModeValue {

    /** Gateway (HTTP) connection.
      */
    case object Gateway extends DbCosmosdbConnectionModeValue("gateway")

    /** Direct connection.
      */
    case object Direct extends DbCosmosdbConnectionModeValue("direct")
  }

  /** Values for [[DbCosmosdbConsistencyLevel]].
    */
  abstract class DbCosmosdbConsistencyLevelValue(val value: String)
  object DbCosmosdbConsistencyLevelValue {

    /** strong.
      */
    case object Strong extends DbCosmosdbConsistencyLevelValue("Strong")

    /** bounded_staleness.
      */
    case object BoundedStaleness extends DbCosmosdbConsistencyLevelValue("BoundedStaleness")

    /** session.
      */
    case object Session extends DbCosmosdbConsistencyLevelValue("Session")

    /** eventual.
      */
    case object Eventual extends DbCosmosdbConsistencyLevelValue("Eventual")

    /** consistent_prefix.
      */
    case object ConsistentPrefix extends DbCosmosdbConsistencyLevelValue("ConsistentPrefix")
  }

  /** Values for [[DbCosmosdbOperationType]].
    */
  @deprecated("No replacement at this time.", "")
  abstract class DbCosmosdbOperationTypeValue(val value: String)
  @annotation.nowarn("cat=deprecation")
  object DbCosmosdbOperationTypeValue {

    /** batch.
      */
    case object Batch extends DbCosmosdbOperationTypeValue("batch")

    /** create.
      */
    case object Create extends DbCosmosdbOperationTypeValue("create")

    /** delete.
      */
    case object Delete extends DbCosmosdbOperationTypeValue("delete")

    /** execute.
      */
    case object Execute extends DbCosmosdbOperationTypeValue("execute")

    /** execute_javascript.
      */
    case object ExecuteJavascript extends DbCosmosdbOperationTypeValue("execute_javascript")

    /** invalid.
      */
    case object Invalid extends DbCosmosdbOperationTypeValue("invalid")

    /** head.
      */
    case object Head extends DbCosmosdbOperationTypeValue("head")

    /** head_feed.
      */
    case object HeadFeed extends DbCosmosdbOperationTypeValue("head_feed")

    /** patch.
      */
    case object Patch extends DbCosmosdbOperationTypeValue("patch")

    /** query.
      */
    case object Query extends DbCosmosdbOperationTypeValue("query")

    /** query_plan.
      */
    case object QueryPlan extends DbCosmosdbOperationTypeValue("query_plan")

    /** read.
      */
    case object Read extends DbCosmosdbOperationTypeValue("read")

    /** read_feed.
      */
    case object ReadFeed extends DbCosmosdbOperationTypeValue("read_feed")

    /** replace.
      */
    case object Replace extends DbCosmosdbOperationTypeValue("replace")

    /** upsert.
      */
    case object Upsert extends DbCosmosdbOperationTypeValue("upsert")
  }

  /** Values for [[DbSystem]].
    */
  abstract class DbSystemValue(val value: String)
  object DbSystemValue {

    /** Some other SQL database. Fallback only. See notes.
      */
    case object OtherSql extends DbSystemValue("other_sql")

    /** Adabas (Adaptable Database System)
      */
    case object Adabas extends DbSystemValue("adabas")

    /** Deprecated, use `intersystems_cache` instead.
      */
    case object Cache extends DbSystemValue("cache")

    /** InterSystems Caché
      */
    case object IntersystemsCache extends DbSystemValue("intersystems_cache")

    /** Apache Cassandra
      */
    case object Cassandra extends DbSystemValue("cassandra")

    /** ClickHouse
      */
    case object Clickhouse extends DbSystemValue("clickhouse")

    /** Deprecated, use `other_sql` instead.
      */
    case object Cloudscape extends DbSystemValue("cloudscape")

    /** CockroachDB
      */
    case object Cockroachdb extends DbSystemValue("cockroachdb")

    /** Deprecated, no replacement at this time.
      */
    case object Coldfusion extends DbSystemValue("coldfusion")

    /** Microsoft Azure Cosmos DB
      */
    case object Cosmosdb extends DbSystemValue("cosmosdb")

    /** Couchbase
      */
    case object Couchbase extends DbSystemValue("couchbase")

    /** CouchDB
      */
    case object Couchdb extends DbSystemValue("couchdb")

    /** IBM Db2
      */
    case object Db2 extends DbSystemValue("db2")

    /** Apache Derby
      */
    case object Derby extends DbSystemValue("derby")

    /** Amazon DynamoDB
      */
    case object Dynamodb extends DbSystemValue("dynamodb")

    /** EnterpriseDB
      */
    case object Edb extends DbSystemValue("edb")

    /** Elasticsearch
      */
    case object Elasticsearch extends DbSystemValue("elasticsearch")

    /** FileMaker
      */
    case object Filemaker extends DbSystemValue("filemaker")

    /** Firebird
      */
    case object Firebird extends DbSystemValue("firebird")

    /** Deprecated, use `other_sql` instead.
      */
    case object Firstsql extends DbSystemValue("firstsql")

    /** Apache Geode
      */
    case object Geode extends DbSystemValue("geode")

    /** H2
      */
    case object H2 extends DbSystemValue("h2")

    /** SAP HANA
      */
    case object Hanadb extends DbSystemValue("hanadb")

    /** Apache HBase
      */
    case object Hbase extends DbSystemValue("hbase")

    /** Apache Hive
      */
    case object Hive extends DbSystemValue("hive")

    /** HyperSQL DataBase
      */
    case object Hsqldb extends DbSystemValue("hsqldb")

    /** InfluxDB
      */
    case object Influxdb extends DbSystemValue("influxdb")

    /** Informix
      */
    case object Informix extends DbSystemValue("informix")

    /** Ingres
      */
    case object Ingres extends DbSystemValue("ingres")

    /** InstantDB
      */
    case object Instantdb extends DbSystemValue("instantdb")

    /** InterBase
      */
    case object Interbase extends DbSystemValue("interbase")

    /** MariaDB (This value has stability level RELEASE CANDIDATE)
      */
    case object Mariadb extends DbSystemValue("mariadb")

    /** SAP MaxDB
      */
    case object Maxdb extends DbSystemValue("maxdb")

    /** Memcached
      */
    case object Memcached extends DbSystemValue("memcached")

    /** MongoDB
      */
    case object Mongodb extends DbSystemValue("mongodb")

    /** Microsoft SQL Server (This value has stability level RELEASE CANDIDATE)
      */
    case object Mssql extends DbSystemValue("mssql")

    /** Deprecated, Microsoft SQL Server Compact is discontinued.
      */
    case object Mssqlcompact extends DbSystemValue("mssqlcompact")

    /** MySQL (This value has stability level RELEASE CANDIDATE)
      */
    case object Mysql extends DbSystemValue("mysql")

    /** Neo4j
      */
    case object Neo4j extends DbSystemValue("neo4j")

    /** Netezza
      */
    case object Netezza extends DbSystemValue("netezza")

    /** OpenSearch
      */
    case object Opensearch extends DbSystemValue("opensearch")

    /** Oracle Database
      */
    case object Oracle extends DbSystemValue("oracle")

    /** Pervasive PSQL
      */
    case object Pervasive extends DbSystemValue("pervasive")

    /** PointBase
      */
    case object Pointbase extends DbSystemValue("pointbase")

    /** PostgreSQL (This value has stability level RELEASE CANDIDATE)
      */
    case object Postgresql extends DbSystemValue("postgresql")

    /** Progress Database
      */
    case object Progress extends DbSystemValue("progress")

    /** Redis
      */
    case object Redis extends DbSystemValue("redis")

    /** Amazon Redshift
      */
    case object Redshift extends DbSystemValue("redshift")

    /** Cloud Spanner
      */
    case object Spanner extends DbSystemValue("spanner")

    /** SQLite
      */
    case object Sqlite extends DbSystemValue("sqlite")

    /** Sybase
      */
    case object Sybase extends DbSystemValue("sybase")

    /** Teradata
      */
    case object Teradata extends DbSystemValue("teradata")

    /** Trino
      */
    case object Trino extends DbSystemValue("trino")

    /** Vertica
      */
    case object Vertica extends DbSystemValue("vertica")
  }

}
