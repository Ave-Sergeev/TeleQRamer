package service.telegram

import config.AppConfig
import service.qr.QR
import service.shortener.Shortener
import service.telegram.TGClient.CanoeClient
import zio.macros.accessible
import zio.{Config, RIO, ZLayer}

@accessible
trait TGBot {
  def run: RIO[AppConfig, Unit]
}

object TGBot {
  val live: ZLayer[CanoeClient with QR with Shortener, Config.Error, TGBot] = TGBotLive.layer
}
