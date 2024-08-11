import sbt.*

object Dependencies {

  object Version {
    val scala        = "2.13.11"
    val zio          = "2.1.9"
    val zioJson      = "0.7.3"
    val zioCats      = "23.1.0.3"
    val zioHttp      = "3.0.0-RC6"
    val zioRedis     = "0.2.0+99-bcdad164-SNAPSHOT"
    val zioSchema    = "1.4.1"
    val zioConfig    = "4.0.2"
    val zioLogging   = "2.3.1"
    val scalaLogging = "3.9.5"
    val logback      = "1.5.8"
    val circe        = "0.14.10"
    val netty        = "4.1.113.Final"
    val canoe        = "0.6.0"
    val sl4j         = "2.0.16"
    val qrGen        = "1.4"
    val quill        = "4.8.3"
    val postgre      = "42.7.4"
    val ulid         = "24.8.0"
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

  object HTTP {
    lazy val zhttp     = "dev.zio" %% "zio-http"  % Version.zioHttp
    lazy val httpNetty = "io.netty" % "netty-all" % Version.netty
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

  object STORAGE {
    lazy val quill   = "io.getquill"   %% "quill-jdbc-zio" % Version.quill
    lazy val postgre = "org.postgresql" % "postgresql"     % Version.postgre
  }

  object CIRCE {
    lazy val core = "io.circe" %% "circe-core" % Version.circe
  }

  object UTILS {
    lazy val ulid = "org.wvlet.airframe" %% "airframe-ulid" % Version.ulid
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
    HTTP.zhttp,
    HTTP.zhttp,
    UTILS.ulid,
    CIRCE.core,
    CONFIG.core,
    CONFIG.refined,
    CONFIG.typesafe,
    CONFIG.magnolia,
    STORAGE.quill,
    STORAGE.postgre,
    TELEGRAM.canoe
  )
}
