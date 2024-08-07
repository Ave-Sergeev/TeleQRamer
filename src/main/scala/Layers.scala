import config.AppConfig
import service.qr.QR
import service.telegram.{TGBot, TGClient}
import zio.Scope

object Layers {

  private val runtime = Scope.default

  private val base = AppConfig.live

  private val services = QR.live >+> TGClient.live >+> TGBot.live

  val all =
    runtime >+>
      base >+>
      services
}
