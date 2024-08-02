import zio.{ZIOAppDefault, _}

object Main extends ZIOAppDefault {

  private val program = for {
    _ <- ZIO.logInfo(s"Start")
  } yield ()

  override def run = program
}
