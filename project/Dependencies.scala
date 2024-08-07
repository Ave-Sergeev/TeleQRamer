import sbt.*

object Dependencies {

  object Version {
    val scala        = "2.13.11"
    val zio          = "2.1.6"
    val zioJson      = "0.7.1"
    val zioCats      = "23.1.0.2"
    val zioRedis     = "0.2.0+99-bcdad164-SNAPSHOT"
    val zioSchema    = "1.3.0"
    val zioConfig    = "4.0.2"
    val zioLogging   = "2.3.0"
    val scalaLogging = "3.9.5"
    val logback      = "1.5.6"
    val circe        = "0.14.9"
    val netty        = "4.1.94.Final"
    val canoe        = "0.6.0"
    val sl4j         = "2.0.12"
    val qrGen        = "1.4"
  }

  object QR {
    lazy val qrgen = "net.glxn" % "qrgen" % Version.qrGen
  }

  object TELEGRAM {
    lazy val canoe = "org.augustjune" %% "canoe" % Version.canoe
  }

  object ZIO {
    lazy val core         = "dev.zio" %% "zio"                   % Version.zio
    lazy val macros       = "dev.zio" %% "zio-macros"            % Version.zio
    lazy val streams      = "dev.zio" %% "zio-streams"           % Version.zio
    lazy val cats         = "dev.zio" %% "zio-interop-cats"      % Version.zioCats
    lazy val json         = "dev.zio" %% "zio-json"              % Version.zioJson
    lazy val redis        = "dev.zio" %% "zio-redis"             % Version.zioRedis
    lazy val redisEmb     = "dev.zio" %% "zio-redis-embedded"    % Version.zioRedis % Test
    lazy val schema       = "dev.zio" %% "zio-schema"            % Version.zioSchema
    lazy val schemaPb     = "dev.zio" %% "zio-schema-protobuf"   % Version.zioSchema
    lazy val schemaJson   = "dev.zio" %% "zio-schema-json"       % Version.zioSchema
    lazy val schemaDerive = "dev.zio" %% "zio-schema-derivation" % Version.zioSchema
  }

  object CONFIG {
    lazy val core     = "dev.zio" %% "zio-config"          % Version.zioConfig
    lazy val refined  = "dev.zio" %% "zio-config-refined"  % Version.zioConfig
    lazy val magnolia = "dev.zio" %% "zio-config-magnolia" % Version.zioConfig
    lazy val typesafe = "dev.zio" %% "zio-config-typesafe" % Version.zioConfig
  }

  object LOGS {
    lazy val sl4j           = "org.slf4j"                   % "slf4j-api"          % Version.sl4j
    lazy val logback        = "ch.qos.logback"              % "logback-classic"    % Version.logback
    lazy val zioLogging     = "dev.zio"                    %% "zio-logging"        % Version.zioLogging
    lazy val zioLoggingLf4j = "dev.zio"                    %% "zio-logging-slf4j2" % Version.zioLogging
    lazy val scalaLogging   = "com.typesafe.scala-logging" %% "scala-logging"      % Version.scalaLogging
  }

  object CIRCE {
    lazy val core = "io.circe" %% "circe-core" % Version.circe
  }

  lazy val globalProjectDependencies = Seq(
    QR.qrgen,
    ZIO.core,
    ZIO.cats,
    ZIO.json,
    ZIO.redis,
    ZIO.macros,
    ZIO.schema,
    ZIO.streams,
    ZIO.redisEmb,
    ZIO.schemaPb,
    ZIO.schemaJson,
    ZIO.schemaDerive,
    LOGS.sl4j,
    LOGS.logback,
    LOGS.zioLogging,
    LOGS.scalaLogging,
    LOGS.zioLoggingLf4j,
    CIRCE.core,
    CONFIG.core,
    CONFIG.refined,
    CONFIG.typesafe,
    CONFIG.magnolia,
    TELEGRAM.canoe
  )
}
