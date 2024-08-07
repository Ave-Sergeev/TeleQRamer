package service.telegram

import canoe.api._
import canoe.models._
import canoe.syntax._
import config._
import service.qr.QR
import service.shortener.Shortener
import service.telegram.TGClient.CanoeClient
import zio._
import zio.interop.catz.concurrentInstance

final case class TGBotLive(
    qrService: QR,
    client: CanoeClient,
    shortener: Shortener,
    config: InterfaceConfig
) extends TGBot {
  implicit val _client: CanoeClient = client

  override def run: RIO[AppConfig, Unit] =
    Bot
      .polling[Task]
      .follow(init)
      .compile
      .drain

  private val init: Scenario[Task, Unit] =
    for {
      chat <- Scenario.expect(command("start").chat)
      _    <- Scenario.eval(chat.send(s"Бот представляет возможность генерации QR-кода и ShortURL"))
      _    <- repeat(chat)
    } yield ()

  private def repeat(chat: Chat): Scenario[Task, Unit] = {
    for {
      // TODO: Можно генерировать QR из shortURL (на будущее)
      _       <- Scenario.eval(chat.send(s"Введите URL"))
      url     <- Scenario.expect(text)
      content <- Scenario.eval(qrService.generate(url))
      _       <- Scenario.eval(chat.send(content))
      token   <- Scenario.eval(shortener.shorten(url))
      _       <- Scenario.eval(chat.send("Наведите камеру и сканируйте QR-код"))
      _       <- Scenario.eval(chat.send(s"Short URL: ${createShortUrl(token)}")) >> repeat(chat)
    } yield ()
  }

  private val createShortUrl = (token: String) => s"${config.urlDomain}/$token"
}

object TGBotLive {

  val layer: ZLayer[CanoeClient with QR with Shortener, Config.Error, TGBot] = ZLayer.fromZIO {
    for {
      config    <- AppConfig.get(_.interface)
      qrService <- ZIO.service[QR]
      shortener <- ZIO.service[Shortener]
      client    <- ZIO.service[CanoeClient]
    } yield TGBotLive(qrService, client, shortener, config)
  }
}
