package service.shortener

import config.AppConfig
import util.ULID
import zio.redis.Redis
import zio.schema.DeriveSchema.gen
import zio.{Config, Task, ZIO, ZLayer}

final case class ShortenerLive(
    redis: Redis,
    config: AppConfig
) extends Shortener {

  override def check(shortUrl: String): Task[Option[String]] = checkKeyInRedis(shortUrl)

  override def shorten(longUrl: String): Task[String] =
    checkKeyInRedis(longUrl).flatMap {
      case Some(value) =>
        ZIO.succeed(value)
      case None =>
        for {
          token <- ULID.nextULIDString
          _     <- redis.set(longUrl, token) // для поиска при создании
          _     <- redis.set(token, longUrl) // для поиска при редиректе
        } yield token
    }

  private def checkKeyInRedis(url: String): Task[Option[String]] =
    redis
      .get(url)
      .returning[String]
      .flatMap {
        case Some(value) => ZIO.succeed(Some(value))
        case None        => ZIO.succeed(None)
      }
}

object ShortenerLive {

  val layer: ZLayer[Redis, Config.Error, ShortenerLive] = ZLayer.fromZIO {
    for {
      config <- AppConfig.get
      redis  <- ZIO.service[Redis]
    } yield ShortenerLive(redis, config)
  }
}
