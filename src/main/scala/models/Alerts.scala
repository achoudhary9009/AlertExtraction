package models

import cats.implicits.toBifunctorOps
import io.circe._
import io.circe.generic.semiauto.deriveDecoder

import java.time.Instant

import java.sql.Timestamp
import scala.util.Try

case class Content(text: String, `type`: String, language: String)
case class Alerts(id: String, contents: List[Content], date: Timestamp, inputType: String)

object Alerts {
  implicit val contentDecoder: Decoder[Content] = deriveDecoder[Content]


  implicit val timestampDecoder: Decoder[Timestamp] = Decoder.decodeString.emap { str =>
    Try {
      val instant = Instant.parse(str)
      Timestamp.from(instant)
    }.toEither.leftMap(_ => "Timestamp")
  }

  implicit val alertsDecoder: Decoder[Alerts] = deriveDecoder[Alerts]

  implicit val listAlertsDecoder: Decoder[List[Alerts]] = Decoder.decodeList[Alerts]
}
