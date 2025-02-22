import sbt.*

object Dependencies {

  object Version {
    val scala        = "2.13.16"
    val zio          = "2.1.15"
    val zioJson      = "0.7.26"
    val zioCats      = "23.1.0.3"
    val zioHttp      = "3.0.1"
    val zioRedis     = "1.0.1"
    val zioSchema    = "1.6.3"
    val zioConfig    = "4.0.3"
    val zioLogging   = "2.4.0"
    val scalaLogging = "3.9.5"
    val sl4j         = "2.0.16"
    val ulid         = "2025.1.6"
    val qrGen        = "1.4"
    val canoe        = "0.6.0"
    val quill        = "4.8.5"
    val circe        = "0.14.10"
    val netty        = "4.1.118.Final"
    val logback      = "1.5.16"
    val postgre      = "42.7.5"
  }

  object ZIO {
    lazy val core          = "dev.zio" %% "zio"                   % Version.zio
    lazy val macros        = "dev.zio" %% "zio-macros"            % Version.zio
    lazy val streams       = "dev.zio" %% "zio-streams"           % Version.zio
    lazy val cats          = "dev.zio" %% "zio-interop-cats"      % Version.zioCats
    lazy val json          = "dev.zio" %% "zio-json"              % Version.zioJson
    lazy val redis         = "dev.zio" %% "zio-redis"             % Version.zioRedis
    lazy val schema        = "dev.zio" %% "zio-schema"            % Version.zioSchema
    lazy val schemaPb      = "dev.zio" %% "zio-schema-protobuf"   % Version.zioSchema
    lazy val schemaJson    = "dev.zio" %% "zio-schema-json"       % Version.zioSchema
    lazy val schemaDerive  = "dev.zio" %% "zio-schema-derivation" % Version.zioSchema
    lazy val redisEmbedded = "dev.zio" %% "zio-redis-embedded"    % Version.zioRedis % Test
  }

  object HTTP {
    lazy val zioHttp   = "dev.zio" %% "zio-http"  % Version.zioHttp
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

  object QR {
    lazy val qrgen = "net.glxn" % "qrgen" % Version.qrGen
  }

  object TELEGRAM {
    lazy val canoe = "org.augustjune" %% "canoe" % Version.canoe
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
    ZIO.schemaPb,
    ZIO.schemaJson,
    ZIO.schemaDerive,
    ZIO.redisEmbedded,
    LOGS.sl4j,
    LOGS.logback,
    LOGS.zioLogging,
    LOGS.scalaLogging,
    LOGS.zioLoggingLf4j,
    HTTP.zioHttp,
    HTTP.httpNetty,
    CIRCE.core,
    UTILS.ulid,
    CONFIG.core,
    CONFIG.refined,
    CONFIG.typesafe,
    CONFIG.magnolia,
    STORAGE.quill,
    STORAGE.postgre,
    TELEGRAM.canoe
  )
}
