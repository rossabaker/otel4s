// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "org.typelevel"
ThisBuild / organizationName := "Typelevel"
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

val Scala213 = "2.13.8"
ThisBuild / crossScalaVersions := Seq(Scala213, "3.1.3")
ThisBuild / scalaVersion := Scala213 // the default Scala

val CatsVersion = "2.8.0"
val CatsEffectVersion = "3.3.14"
val FS2Version = "3.3.0"
val MUnitVersion = "0.7.29"
val MUnitCatsEffectVersion = "1.0.7"
val OpenTelemetryVersion = "1.15.0"
val ScodecVersion = "1.1.34"

lazy val scalaReflectDependency = Def.settings(
  libraryDependencies ++= {
    if (tlIsScala3.value) Nil
    else Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided)
  }
)

lazy val root = tlCrossRootProject
  .aggregate(
    `core-common`,
    `core-metrics`,
    `core-tracing`,
    core,
    `testkit-common`,
    `testkit-metrics`,
    testkit,
    `java-common`,
    `java-metrics`,
    `java-tracing`,
    java
  )
  .settings(name := "otel4s")

lazy val `core-common` = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core/common"))
  .settings(
    name := "otel4s-core-common",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % CatsVersion,
      "org.typelevel" %%% "cats-effect-kernel" % CatsEffectVersion,
      "org.scalameta" %%% "munit" % MUnitVersion % Test
    )
  )

lazy val `core-metrics` = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core/metrics"))
  .dependsOn(`core-common`)
  .settings(scalaReflectDependency)
  .settings(
    name := "otel4s-core-metrics",
    libraryDependencies ++= Seq(
      "org.scalameta" %%% "munit" % MUnitVersion % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % MUnitCatsEffectVersion % Test,
      "org.typelevel" %%% "cats-effect-testkit" % CatsEffectVersion % Test
    )
  )

lazy val `core-tracing` = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core/tracing"))
  .dependsOn(`core-common`)
  .settings(scalaReflectDependency)
  .settings(
    name := "otel4s-core-tracing",
    libraryDependencies ++= Seq(
      "org.scodec" %%% "scodec-bits" % ScodecVersion,
      "org.scalameta" %%% "munit" % MUnitVersion % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % MUnitCatsEffectVersion % Test,
      "org.typelevel" %%% "cats-effect-testkit" % CatsEffectVersion % Test
    )
  )

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core/all"))
  .dependsOn(`core-common`, `core-metrics`, `core-tracing`)
  .settings(
    name := "otel4s-core"
  )

lazy val `testkit-common` = crossProject(JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("testkit/common"))
  .dependsOn(`core-common`)
  .settings(
    name := "otel4s-testkit-common"
  )

lazy val `testkit-metrics` = crossProject(JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("testkit/metrics"))
  .dependsOn(`testkit-common`, `core-metrics`)
  .settings(
    name := "otel4s-testkit-metrics"
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "io.opentelemetry" % "opentelemetry-api" % OpenTelemetryVersion,
      "io.opentelemetry" % "opentelemetry-sdk" % OpenTelemetryVersion,
      "io.opentelemetry" % "opentelemetry-sdk-testing" % OpenTelemetryVersion
    )
  )

lazy val testkit = crossProject(JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("testkit/all"))
  .dependsOn(`testkit-common`, `testkit-metrics`)
  .settings(
    name := "otel4s-testkit"
  )

lazy val `java-common` = project
  .in(file("java/common"))
  .dependsOn(`core-common`.jvm, `testkit-common`.jvm)
  .settings(
    name := "otel4s-java-common",
    libraryDependencies ++= Seq(
      "io.opentelemetry" % "opentelemetry-api" % OpenTelemetryVersion
    )
  )

lazy val `java-metrics` = project
  .in(file("java/metrics"))
  .dependsOn(`java-common`, `core-metrics`.jvm, `testkit-metrics`.jvm)
  .settings(
    name := "otel4s-java-metrics",
    libraryDependencies ++= Seq(
      "io.opentelemetry" % "opentelemetry-api" % OpenTelemetryVersion,
      "io.opentelemetry" % "opentelemetry-sdk" % OpenTelemetryVersion % Test,
      "io.opentelemetry" % "opentelemetry-sdk-testing" % OpenTelemetryVersion % Test,
      "org.scalameta" %% "munit" % MUnitVersion % Test,
      "org.typelevel" %% "munit-cats-effect-3" % MUnitCatsEffectVersion % Test
    )
  )

lazy val `java-tracing` = project
  .in(file("java/tracing"))
  .dependsOn(`java-common`, `core-metrics`.jvm, `testkit-metrics`.jvm)
  .settings(
    name := "otel4s-java-tracing",
    libraryDependencies ++= Seq(
      "io.opentelemetry" % "opentelemetry-api" % OpenTelemetryVersion,
      "io.opentelemetry" % "opentelemetry-sdk" % OpenTelemetryVersion % Test,
      "io.opentelemetry" % "opentelemetry-sdk-testing" % OpenTelemetryVersion % Test,
      "co.fs2" %%% "fs2-core" % FS2Version % Test,
      "org.scalameta" %% "munit" % MUnitVersion % Test,
      "org.typelevel" %% "munit-cats-effect-3" % MUnitCatsEffectVersion % Test
    )
  )

lazy val java = project
  .in(file("java/all"))
  .dependsOn(core.jvm, `java-metrics`, `java-tracing`)
  .settings(
    name := "otel4s-java"
  )

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)
