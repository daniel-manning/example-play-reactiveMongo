package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, AnyContent, Controller, Request}
import services.RssService

@Singleton
class LoadingEarthquakeController @Inject()(rssService: RssService) extends Controller {

  def load = Action { implicit request: Request[AnyContent] =>
    val earthquakes = rssService.getListOfEarthquakes
    Ok(views.html.loading_earthquakes("Loading Earthquakes")(earthquakes))
  }
}
