package services

import connectors.RssConnector
import javax.inject.Inject
import models.Earthquake
import org.joda.time.format.DateTimeFormat

import scala.xml.Node

class RssService @Inject()(rssConnector:RssConnector){

  def nodeToEarthquake(entryNode:Node):Option[Earthquake] = {

    val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ZZZ")

    val categoryString = entryNode.child.filter(node => node.label == "category" && node.attributes.exists(_.value.text == "Magnitude")).head.attribute("term").get.toList.head
    val latLongString = entryNode.child.filter(node => node.label == "summary").text
    val latLongRegex = "<dl><dt>Time</dt><dd>([\\d -:UTC]+)</dd><dd>.*</dd><dt>Location</dt><dd>([\\d.]+)&deg;([NS]) ([\\d.]+)&deg;([EW])</dd>".r
    latLongRegex.findFirstMatchIn(latLongString).map( m => {
      val date = dateTimeFormatter.parseDateTime(m.group(1))
      Earthquake( date,
                  if(m.group(3) == "N") m.group(2).toDouble else -1* m.group(2).toDouble,
                  if(m.group(5) == "E") m.group(4).toDouble else -1* m.group(4).toDouble,
                  categoryString.toString()
    )})
  }

  def getListOfEarthquakes:List[Earthquake] = {
    val xml = rssConnector.loadXmlFromFeed()
    val nodes = xml \\ "entry"
    val earthquakes = nodes.theSeq.map(nodeToEarthquake).toList
    earthquakes.filter(_.isDefined).map(_.get)
  }

}