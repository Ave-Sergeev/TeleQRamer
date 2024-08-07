package service.qr

import config.{AppConfig, QRConfig}
import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType
import zio.{Config, Layer, Task, ZIO, ZLayer}

final case class QRLive(
    config: QRConfig
) extends QR {

  override def generate(url: String): Task[Array[Byte]] =
    ZIO.attempt {
      QRCode
        .from(url)
        .to(ImageType.JPG)
        .withSize(config.width, config.height)
        .withCharset("UTF-8")
        .stream()
        .toByteArray
    }
}

object QRLive {

  val layer: Layer[Config.Error, QR] = ZLayer.fromZIO {
    AppConfig.get(_.qr).map(config => QRLive(config))
  }
}
