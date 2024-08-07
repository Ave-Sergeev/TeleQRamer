package service.qr

import canoe.models.outgoing.PhotoContent
import zio.macros.accessible
import zio.{Config, Layer, Task}

@accessible
trait QR {
  def generate(url: String): Task[PhotoContent]
}

object QR {
  val live: Layer[Config.Error, QR] = QRLive.layer
}
