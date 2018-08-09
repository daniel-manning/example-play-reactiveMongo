package connectors

import javax.xml.parsers.SAXParserFactory

import scala.xml.{Node, XML}

class RssConnector {

  def loadXmlFromFeed():Node = {
    //should be loading from the all earthquake feed from the USGS
    //https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.atom
    val spf = SAXParserFactory.newInstance()
    spf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    spf.setFeature("http://xml.org/sax/features/external-general-entities", false)
    spf.setXIncludeAware(false)
    //spf.setExpandEntityReferences(false)
    val saxParser = spf.newSAXParser()
    XML.withSAXParser(saxParser).load("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.atom")
  }

}
