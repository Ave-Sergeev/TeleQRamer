package service.shortener

import zio.{Config, Task, ZLayer}
import zio.macros.accessible
import zio.redis.Redis

@accessible
trait Shortener {
  def check(url: String): Task[Option[String]]
  def shorten(url: String): Task[String]
}

object Shortener {
  val live: ZLayer[Redis, Config.Error, Shortener] = ShortenerLive.layer
}
