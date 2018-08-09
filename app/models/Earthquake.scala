package models

import org.joda.time.DateTime
import play.api.libs.json.{Json, OFormat}

case class Earthquake(date:DateTime, latitude:Double,longitude:Double, magnitude:String)

object Earthquake {
  implicit val format: OFormat[Earthquake] = Json.format[Earthquake]
}