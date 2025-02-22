import config.AppConfig
import http.HTTPServer
import service.qr.QR
import service.shortener.Shortener
import service.telegram.{TGBot, TGClient}
import storage.redis.redisProtobufCodecLayer
import zio.Scope
import zio.http.Client

object Layers {

  private val runtime = Scope.default

  private val base = AppConfig.live

  private val services =
    Client.default >+>
      redisProtobufCodecLayer >+>
      HTTPServer.live >+>
      Shortener.live >+>
      TGClient.live >+>
      QR.live >+>
      TGBot.live

  val all =
    runtime >+>
      base >+>
      services
}
