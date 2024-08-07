package http

import exception.Exceptions._
import util.ULID
import zio.http.{Request, Response}
import zio.{RIO, Scope, ZIO, ZIOAspect}

package object handlers {

  def handleREST[R, E <: Throwable, A](
      request: Request
  )(
      effect: ZIO[R, E, A]
  ): RIO[R with Scope, A] = {
    val path = s"${request.method} ${request.url.path}"

    for {
      traceId <- ULID.nextULIDString
      annotations = ZIOAspect.annotated(
        "method"  -> path,
        "traceId" -> traceId
      )
      result <- effect.tapError(err => ZIO.logError(err.getMessage)) @@ annotations
    } yield result
  }

  val exceptionHandler: Throwable => Response = {
    case err: InternalException => Response.internalServerError(s"Exception: $err")
    case err                    => Response.badRequest(s"Exception: $err")
  }
}
