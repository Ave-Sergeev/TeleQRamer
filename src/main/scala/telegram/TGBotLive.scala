package telegram

import canoe.api._
import canoe.models._
import canoe.syntax._
import config.AppConfig
import fs2.Pipe
import telegram.TGClient.BotTask
import zio._
import zio.interop.catz.concurrentInstance

final case class TGBotLive(
    client: TelegramClient[BotTask]
) extends TGBot {
  implicit val _client: TelegramClient[BotTask] = client

  override def run: RIO[AppConfig, Unit] =
    Bot
      .polling[BotTask]
      .follow(init)             // Follow script on incoming messages
      .through(answerCallbacks) // Follow the callback response script
      .compile
      .drain

  private val answerCallbacks: Pipe[BotTask, Update, Update] =
    _.evalTap {
      case CallbackButtonSelected(_, query) =>
        query.data match {
          case Some(_) =>
            for {
              _ <- query.finish
            } yield ()
          case _ => ZIO.unit
        }
      case _ => ZIO.unit
    }

  private val init: Scenario[BotTask, Unit] =
    for {
      _ <- Scenario.expect(command("start").chat)
      _ <- Scenario.expect(command("callback"))
    } yield ()
}

object TGBotLive {

  val layer: RLayer[TelegramClient[BotTask], TGBot] = ZLayer.fromZIO {
    ZIO.serviceWith[TelegramClient[BotTask]](client => TGBotLive(client))
  }
}
