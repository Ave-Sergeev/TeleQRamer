package http.endpoints

import exception.Exceptions.InternalException
import http.handlers._
import service.shortener.Shortener
import zio.http._
import zio.{Scope, ZIO}

object Conductor {

  val routes: Routes[Shortener with Scope, Nothing] =
    Routes(
      Method.GET / string("token") -> handler { (token: String, request: Request) =>
        handleREST(request) {
          for {
            longUrl <- Shortener.check(token).flatMap {
              case Some(url) => ZIO.succeed(url)
              case None      => ZIO.fail(InternalException(s"Redirection not possible"))
            }
            url <- ZIO.fromEither(URL.decode(longUrl))
          } yield Response.redirect(url)
        }
      }
    )
      .handleError(exceptionHandler)
}
