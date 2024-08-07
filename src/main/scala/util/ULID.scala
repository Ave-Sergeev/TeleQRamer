package util

import wvlet.airframe.ulid.ULID
import zio.{IO, UIO, ZIO}

import java.time.LocalDateTime
import java.util.{TimeZone, UUID}

package object ULID {

  private val tz = TimeZone.getDefault.toZoneId

  def nextULID: IO[Throwable, ULID] = ZIO.succeed(ULID.newULID)

  def nextULIDString: UIO[String] = ZIO.succeed(ULID.newULIDString)

  def nextUUID: UIO[UUID] = ZIO.succeed(ULID.newULID.toUUID)

  def nextUUIDString: UIO[String] = ZIO.succeed(ULID.newULID.toUUID.toString)

  def getTimeFromULID(ulid: String): LocalDateTime = getTimeFromULID(ULID.fromString(ulid))

  def getTimeFromUUID(uuid: String): LocalDateTime = getTimeFromUUID(UUID.fromString(uuid))

  private def getTimeFromULID(ulid: ULID): LocalDateTime = LocalDateTime.ofInstant(ulid.toInstant, tz)

  private def getTimeFromUUID(uuid: UUID): LocalDateTime = getTimeFromULID(ULID.fromUUID(uuid))
}
