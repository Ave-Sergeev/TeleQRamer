import telegram.TGBot
import zio.Console.printLine
import zio.logging.backend.SLF4J
import zio.{ZIOAppDefault, _}

object Main extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = zio.Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  private val program = for {
    _ <- ZIO.logInfo(s"Server for bot is running. Press Ctrl-C to stop.")
    _ <- TGBot.run
  } yield ()

  override def run: UIO[ExitCode] =
    program
      .provide(Layers.all)
      .foldZIO(
        err => printLine(s"Execution failed with: $err").exitCode,
        _ => ZIO.succeed(ExitCode.success)
      )
}
