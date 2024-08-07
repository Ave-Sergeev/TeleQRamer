package config

import zio.Config.Secret
import zio.config.magnolia.deriveConfig
import zio.config.{toKebabCase, ConfigOps}
import zio.{Config, IO, Layer, ZIO, ZLayer}

case class QRConfig(
    width: Int,
    height: Int
)

case class TelegramConfig(
    token: Secret
)

case class RedisConfig(
    host: String,
    port: Int,
    username: Option[Secret],
    secret: Secret
)

case class InterfaceConfig(
    port: Int,
    urlDomain: String
)

case class AppConfig(
    qr: QRConfig,
    redis: RedisConfig,
    telegram: TelegramConfig,
    interface: InterfaceConfig
)

object AppConfig {

  def get: IO[Config.Error, AppConfig] = ZIO.config[AppConfig](configDescriptor)

  def get[A](f: AppConfig => A): IO[Config.Error, A] = get.map(f)

  implicit val configDescriptor: Config[AppConfig] = (
    deriveConfig[QRConfig].nested("qrConfig") zip
      deriveConfig[RedisConfig].nested("redisConfig") zip
      deriveConfig[TelegramConfig].nested("telegramConfig") zip
      deriveConfig[InterfaceConfig].nested("interfaceConfig")
  )
    .to[AppConfig]
    .mapKey(toKebabCase)

  val live: Layer[Config.Error, AppConfig] = ZLayer.fromZIO {
    ZIO.config[AppConfig](configDescriptor)
  }
}
