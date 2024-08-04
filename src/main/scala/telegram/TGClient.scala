package telegram

import canoe.api.TelegramClient
import config.AppConfig
import util.Secret.SecretOps
import zio.interop.catz._
import zio.{RIO, RLayer, Scope, ZLayer}

object TGClient {

  type BotTask[A] = RIO[AppConfig, A]

  val live: RLayer[AppConfig with Scope, TelegramClient[BotTask]] =
    ZLayer.fromZIO {
      for {
        config <- AppConfig.get(_.telegram)
        client <- TelegramClient[BotTask](config.token.secretToString).toScopedZIO
      } yield client
    }
}
