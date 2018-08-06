package services

import connectors.RssConnector
import models.Earthquake
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class RssServiceSpec extends FlatSpec with Matchers {

  "rssService" should "load a collection of Earthquakes from an ATOM Feed" in {
    val rssService:RssService = new RssService(new TestFeedRssConnector())

    val earthquakes:List[Earthquake] = rssService.getListofEarthquakes
    earthquakes.size shouldBe 2
  }

}

class TestFeedRssConnector extends RssConnector {
  override def loadXmlFromFeed():String = {
    Source.fromURL(getClass.getResource("/EarthQuakeFeed")).mkString
  }
}


