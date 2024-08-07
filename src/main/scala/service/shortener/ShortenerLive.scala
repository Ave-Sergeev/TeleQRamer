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

  override def check(shortUrl: String): Task[Option[String]] = checkInRedis(shortUrl)

  override def shorten(longUrl: String): Task[String] =
    checkInRedis(longUrl).flatMap {
      case Some(value) =>
        ZIO.succeed(value)
      case None =>
        for {
          token <- ULID.nextULIDString
          // TODO: Две записи в редис - для поиска при создании и редиректе (временно)
          _ <- setToRedis(longUrl, token) zipPar setToRedis(token, longUrl)
        } yield token
    }

  private val setToRedis = (key: String, value: String) => redis.set(key, value)

  private def checkInRedis(url: String): Task[Option[String]] =
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
