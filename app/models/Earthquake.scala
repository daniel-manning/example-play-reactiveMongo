package models

import org.joda.time.LocalDate

case class Feed(title:Title, updated:Updated, author:Author, id:Id, link:Link, icon:Icon, entries:List[Entry])
case class Title(value:String)
case class Updated(value:LocalDate)
case class Author(name:String, uri:String)
case class Id(value:String)
case class Link()
case class Icon()

case class Entry()

case class Earthquake()

