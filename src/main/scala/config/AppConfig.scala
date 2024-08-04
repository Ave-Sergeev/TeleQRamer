package config

import zio.Config.Secret
import zio.config.magnolia.deriveConfig
import zio.config.{toKebabCase, ConfigOps}
import zio.{Config, Layer, URIO, ZIO, ZLayer}

case class TelegramConfig(
    token: Secret
)

case class AppConfig(
    telegram: TelegramConfig
)

object AppConfig {

  def get: URIO[AppConfig, AppConfig] = ZIO.service[AppConfig]

  def get[A](f: AppConfig => A): URIO[AppConfig, A] = get.map(f)

  implicit val configDescriptor: Config[AppConfig] =
    deriveConfig[TelegramConfig]
      .nested("telegramConfig")
      .to[AppConfig]
      .mapKey(toKebabCase)

  val live: Layer[Config.Error, AppConfig] = ZLayer.fromZIO {
    ZIO.config[AppConfig](configDescriptor)
  }
}
