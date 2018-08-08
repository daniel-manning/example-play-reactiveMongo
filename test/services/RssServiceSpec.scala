package services

import connectors.RssConnector
import models.Earthquake
import org.joda.time.format.DateTimeFormat
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class RssServiceSpec extends FlatSpec with Matchers {

  "rssService" should "load a collection of Earthquakes from an ATOM Feed" in {
    val rssService:RssService = new RssService(new TestFeedRssConnector())

    val earthquakes:List[Earthquake] = rssService.getListOfEarthquakes
    earthquakes.size shouldBe 2
  }

  it should "transform an xml node into an earthquake class" in {
    val entryNode = <entry><id>urn:earthquake-usgs-gov:nc:73065646</id><title>M 0.8 - 10km E of Mammoth Lakes, CA</title><updated>2018-08-06T20:32:23.313Z</updated><link rel="alternate" type="text/html" href="https://earthquake.usgs.gov/earthquakes/eventpage/nc73065646"/><summary type="html"><![CDATA[<dl><dt>Time</dt><dd>2018-08-06 20:30:46 UTC</dd><dd>2018-08-06 12:30:46 -08:00 at epicenter</dd><dt>Location</dt><dd>37.632&deg;N 118.870&deg;W</dd><dt>Depth</dt><dd>2.88 km (1.79 mi)</dd></dl>]]></summary><georss:point>37.632 -118.8703308</georss:point><georss:elev>-2880</georss:elev><category label="Age" term="Past Hour"/><category label="Magnitude" term="Magnitude 0"/><category label="Contributor" term="nc"/><category label="Author" term="nc"/></entry>

    val rssService:RssService = new RssService(new TestFeedRssConnector())
    val earthquake:Option[Earthquake] = rssService.nodeToEarthquake(entryNode)
    val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ZZZ")
    earthquake shouldBe Some(Earthquake(dateTimeFormatter.parseDateTime("2018-08-06 20:30:46 UTC"), 37.632, -118.870, "Magnitude 0"))
  }

}

class TestFeedRssConnector extends RssConnector {
  override def loadXmlFromFeed():Elem = {
    XML.load(getClass.getResource("/EarthQuakeFeed"))
  }
}


