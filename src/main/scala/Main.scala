import _root_.http.HTTPServer
import service.telegram.TGBot
import zio.Console.printLine
import zio.logging.backend.SLF4J
import zio.{ZIOAppDefault, _}

object Main extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = zio.Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  private val program = for {
    _    <- ZIO.logInfo(s"Server for bot is running. Press Ctrl-C to stop.")
    http <- HTTPServer.start.exitCode.fork
    bot  <- TGBot.run.exitCode.fork
    code <- http.join <&> bot.join
  } yield code

  override def run: UIO[ExitCode] =
    program
      .provide(Layers.all)
      .foldZIO(
        err => printLine(s"Execution failed with: $err").exitCode,
        _ => ZIO.succeed(ExitCode.success)
      )
}
