package services

import connectors.RssConnector
import javax.inject.Inject
import models.{Earthquake, Entry, Feed}
import com.thoughtworks.xstream.XStream

class RssService @Inject()(rssConnector:RssConnector){

  def getListofEarthquakes:List[Earthquake] = {
    val xml = rssConnector.loadXmlFromFeed()



    val xstream = new XStream
    xstream.alias("feed", classOf[Feed])
    xstream.addImplicitCollection(classOf[Entry], "entries")
    xstream.alias("entry", classOf[Entry])
    xstream.fromXML(xml).asInstanceOf[List[Earthquake]]
  }

}