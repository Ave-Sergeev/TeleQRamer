package http

import config.AppConfig
import http.endpoints._
import service.shortener.Shortener
import zio.http.Server
import zio.http.netty.NettyConfig
import zio.http.netty.NettyConfig.LeakDetectionLevel
import zio.{RLayer, Scope, URIO, ZLayer}

object HTTPServer {

  private val allRoutes = Health.routes ++ Conductor.routes

  def start: URIO[Shortener with Scope with Server, Nothing] = Server.serve(allRoutes)

  private val nettyConfigLayer = ZLayer.succeed(
    NettyConfig.default
      .leakDetection(LeakDetectionLevel.DISABLED)
  )

  private val serverConfigLayer = ZLayer.fromZIO {
    AppConfig.get(_.interface).map { config =>
      Server.Config.default.port(config.port)
    }
  }

  lazy val live: RLayer[AppConfig, Server] = (serverConfigLayer ++ nettyConfigLayer) >>> Server.customized
}
