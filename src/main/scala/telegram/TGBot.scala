package telegram

import canoe.api.TelegramClient
import config.AppConfig
import telegram.TGClient.BotTask
import zio.macros.accessible
import zio.{RIO, RLayer}

@accessible
trait TGBot {
  def run: RIO[AppConfig, Unit]
}

object TGBot {
  val live: RLayer[TelegramClient[BotTask], TGBot] = TGBotLive.layer
}
