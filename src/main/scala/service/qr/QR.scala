package service.qr

import zio.macros.accessible
import zio.{Config, Layer, Task}

@accessible
trait QR {
  def generate(url: String): Task[Array[Byte]]
}

object QR {
  val live: Layer[Config.Error, QR] = QRLive.layer
}
