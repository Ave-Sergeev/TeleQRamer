package service.telegram

import canoe.api._
import canoe.models._
import canoe.models.outgoing.PhotoContent
import canoe.syntax._
import config.AppConfig
import service.qr.QR
import service.telegram.TGClient.CanoeClient
import zio._
import zio.interop.catz.concurrentInstance

final case class TGBotLive(
    qrService: QR,
    config: AppConfig,
    client: CanoeClient
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
      _    <- repeat(chat)
    } yield ()

  private def repeat(chat: Chat): Scenario[Task, Unit] =
    for {
      _    <- Scenario.eval(chat.send(s"Введите URL"))
      url  <- Scenario.expect(text)
      file <- Scenario.eval(qrService.generate(url))
      qrCode = PhotoContent(InputFile.Upload("qrCode", file))
      _ <- Scenario.eval(chat.send(qrCode))
      _ <- Scenario.eval(chat.send("Наведите камеру и сканируйте QR code")) >> repeat(chat)
    } yield ()
}

object TGBotLive {

  val layer: ZLayer[CanoeClient with QR, Config.Error, TGBot] = ZLayer.fromZIO {
    for {
      config    <- AppConfig.get
      qrService <- ZIO.service[QR]
      client    <- ZIO.service[CanoeClient]
    } yield TGBotLive(qrService, config, client)
  }
}
