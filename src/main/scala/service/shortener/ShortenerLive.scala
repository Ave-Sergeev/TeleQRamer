package service.shortener

import config.AppConfig
import util.zio.ULID
import zio.redis.Redis
import zio.{Config, Task, ZIO, ZLayer}

final case class ShortenerLive(
    redis: Redis,
    config: AppConfig
) extends Shortener {

  override def check(shortUrl: String): Task[Option[String]] = checkInRedis(shortUrl)

  override def shorten(longUrl: String): Task[String] =
    checkInRedis(longUrl).flatMap {
      case Some(token) =>
        ZIO.succeed(token)
      case None =>
        for {
          token <- ULID.newEffectULIDString
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
        case Some(token) => ZIO.some(token)
        case None        => ZIO.none
      }
}

object ShortenerLive {

  val layer: ZLayer[Redis, Config.Error, Shortener] = ZLayer.fromZIO {
    for {
      config <- AppConfig.get
      redis  <- ZIO.service[Redis]
    } yield ShortenerLive(redis, config)
  }
}
