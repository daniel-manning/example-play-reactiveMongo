package services

import connectors.RssConnector
import javax.inject.Inject
import models.Earthquake

import scala.xml.{Elem, Node}

class RssService @Inject()(rssConnector:RssConnector){

  def nodeToEarthquake(entryNode:Node):Option[Earthquake] = {

    val latLongString = entryNode.child.filter(node => node.label == "summary").text
    val latLongRegex = "<dt>Location</dt><dd>([\\d.]+)&deg;([NS]) ([\\d.]+)&deg;([EW])</dd>".r
    latLongRegex.findFirstMatchIn(latLongString).map( m =>
      Earthquake( if(m.group(2) == "N") m.group(1).toDouble else -1* m.group(1).toDouble,
                  if(m.group(4) == "E") m.group(3).toDouble else -1* m.group(3).toDouble
    ))
  }

  def getListOfEarthquakes:List[Earthquake] = {
    val xml = rssConnector.loadXmlFromFeed()
    val nodes = xml \\ "entry"
    val earthquakes = nodes.theSeq.map(nodeToEarthquake).toList
    earthquakes.filter(_.isDefined).map(_.get)
  }

}