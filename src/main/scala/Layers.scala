import config.AppConfig
import telegram.{TGBot, TGClient}
import zio.Scope

object Layers {

  private val runtime = Scope.default

  private val base = AppConfig.live

  val all =
    runtime >+>
      base >+>
      TGClient.live >+>
      TGBot.live
}
