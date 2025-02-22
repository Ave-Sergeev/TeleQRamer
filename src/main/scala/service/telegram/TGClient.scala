package service.telegram

import canoe.api.TelegramClient
import config.AppConfig
import util.config.Secret.SecretOps
import zio.interop.catz._
import zio.{RLayer, Scope, Task, ZLayer}

object TGClient {

  type CanoeClient = TelegramClient[Task]

  val live: RLayer[Scope, TelegramClient[Task]] = ZLayer.fromZIO {
    for {
      config <- AppConfig.get(_.telegram)
      client <- TelegramClient[Task](config.token.secretToString).toScopedZIO
    } yield client
  }
}
