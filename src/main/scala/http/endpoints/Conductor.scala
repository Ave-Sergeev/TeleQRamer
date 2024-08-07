package http.endpoints

import exception.Exceptions.InternalException
import http.handlers._
import service.shortener.Shortener
import zio.http._
import zio.{Scope, ZIO}

object Conductor {

  def routes: HttpApp[Shortener with Scope] =
    Routes(
      Method.GET / string("token") -> handler { (token: String, request: Request) =>
        handleREST(request) {
          for {
            longUrl <- Shortener.check(token).flatMap {
              case Some(url) => ZIO.succeed(url)
              case None      => ZIO.fail(InternalException(s"Перенаправление невозможно"))
            }
            url <- ZIO.fromEither(URL.decode(longUrl))
          } yield Response.redirect(url)
        }
      }
    )
      .handleError(exceptionHandler)
      .toHttpApp
}
