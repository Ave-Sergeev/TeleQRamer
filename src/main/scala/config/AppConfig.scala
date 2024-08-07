package config

import zio.Config.Secret
import zio.config.magnolia.deriveConfig
import zio.config.{toKebabCase, ConfigOps}
import zio.{Config, IO, Layer, ZIO, ZLayer}

case class TelegramConfig(
    token: Secret
)

case class QRConfig(
    width: Int,
    height: Int
)

case class AppConfig(
    telegram: TelegramConfig,
    qr: QRConfig
)

object AppConfig {

  def get: IO[Config.Error, AppConfig] = ZIO.config[AppConfig](configDescriptor)

  def get[A](f: AppConfig => A): IO[Config.Error, A] = get.map(f)

  implicit val configDescriptor: Config[AppConfig] = (
    deriveConfig[TelegramConfig].nested("telegramConfig") zip
      deriveConfig[QRConfig].nested("qrConfig")
  )
    .to[AppConfig]
    .mapKey(toKebabCase)

  val live: Layer[Config.Error, AppConfig] = ZLayer.fromZIO {
    ZIO.config[AppConfig](configDescriptor)
  }
}
