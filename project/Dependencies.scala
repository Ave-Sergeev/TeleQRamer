import sbt.*

object Dependencies {

  object Version {
    val scala        = "2.13.11"
    val canoe        = "0.6.0"
    val zio          = "2.1.6"
    val sl4j         = "2.0.12"
    val zioHttp      = "3.0.0-RC9"
    val zioCats      = "23.1.0.2"
    val zioJson      = "0.7.1"
    val zioLogging   = "2.3.0"
    val scalaLogging = "3.9.5"
    val pureConfig   = "0.17.7"
    val derevo       = "0.13.0"
    val logback      = "1.5.6"
    val circe        = "0.14.9"
    val sttp         = "3.9.7"
    val netty        = "4.1.94.Final"
  }

  lazy val canoe = "org.augustjune" %% "canoe" % Version.canoe

  object ZIO {
    lazy val core    = "dev.zio" %% "zio"              % Version.zio
    lazy val http    = "dev.zio" %% "zio-http"         % Version.zioHttp
    lazy val macros  = "dev.zio" %% "zio-macros"       % Version.zio
    lazy val streams = "dev.zio" %% "zio-streams"      % Version.zio
    lazy val zioJson = "dev.zio" %% "zio-json"         % Version.zioJson
    lazy val cats    = "dev.zio" %% "zio-interop-cats" % Version.zioCats
  }

  object HTTP {
    lazy val zhttp     = "dev.zio" %% "zio-http"  % Version.zioHttp
    lazy val httpNetty = "io.netty" % "netty-all" % Version.netty
  }

  object CIRCE {
    lazy val core    = "io.circe" %% "circe-core"    % Version.circe
    lazy val generic = "io.circe" %% "circe-generic" % Version.circe
    lazy val parse   = "io.circe" %% "circe-parser"  % Version.circe
  }

  object DEREVO {
    lazy val core       = "tf.tofu" %% "derevo-core"       % Version.derevo
    lazy val pureConfig = "tf.tofu" %% "derevo-pureconfig" % Version.derevo
  }

  object CONFIG {
    lazy val pureConfig = "com.github.pureconfig" %% "pureconfig" % Version.pureConfig
  }

  object LOGS {
    lazy val core           = "ch.qos.logback"              % "logback-classic"    % Version.logback
    lazy val sl4j           = "org.slf4j"                   % "slf4j-api"          % Version.sl4j
    lazy val zioLogging     = "dev.zio"                    %% "zio-logging"        % Version.zioLogging
    lazy val zioLoggingLf4j = "dev.zio"                    %% "zio-logging-slf4j2" % Version.zioLogging
    lazy val scalaLogging   = "com.typesafe.scala-logging" %% "scala-logging"      % Version.scalaLogging
  }

  lazy val globalProjectDependencies = Seq(
    canoe,
    ZIO.http,
    ZIO.core,
    ZIO.cats,
    ZIO.macros,
    ZIO.streams,
    ZIO.zioJson,
    LOGS.core,
    LOGS.sl4j,
    LOGS.zioLogging,
    LOGS.zioLoggingLf4j,
    LOGS.scalaLogging,
    HTTP.zhttp,
    HTTP.httpNetty,
    CIRCE.core,
    CIRCE.parse,
    CIRCE.generic,
    CONFIG.pureConfig,
    DEREVO.core,
    DEREVO.pureConfig
  )
}
