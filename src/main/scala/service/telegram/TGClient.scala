package service.telegram

import canoe.api.TelegramClient
import config.AppConfig
import util.Secret.SecretOps
import zio.interop.catz._
import zio.{Scope, Task, ZLayer}

object TGClient {

  type CanoeClient = TelegramClient[Task]

  val live: ZLayer[Scope, Throwable, CanoeClient] =
    ZLayer.fromZIO {
      for {
        config <- AppConfig.get(_.telegram)
        client <- TelegramClient[Task](config.token.secretToString).toScopedZIO
      } yield client
    }
}
