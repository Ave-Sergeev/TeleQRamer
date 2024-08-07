import config.AppConfig
import http.HTTPServer
import service.qr.QR
import service.shortener.Shortener
import service.telegram.{TGBot, TGClient}
import storage.redis.redisProtobufCodecLayer
import zio.Scope
import zio.http.{Client, ZClient}

object Layers {

  private val runtime = Scope.default

  private val base = AppConfig.live

  private val services = Client.default >+> ZClient.default >+> redisProtobufCodecLayer

  val all =
    runtime >+>
      base >+>
      services >+>
      HTTPServer.live >+>
      Shortener.live >+>
      QR.live >+>
      TGClient.live >+>
      TGBot.live
}
