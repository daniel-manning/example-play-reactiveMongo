package models

import org.joda.time.DateTime

case class Earthquake(date:DateTime, latitude:Double,longitude:Double, magnitude:String)

