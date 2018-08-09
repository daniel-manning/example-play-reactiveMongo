package controllers

import javax.inject.{Inject, Singleton}
import models.Earthquake
import play.api.mvc.{Action, AnyContent, Controller, Request}
import services.DataService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class CrudEarthquakeController @Inject()(dataService: DataService) extends Controller{

  def create(id:String)= Action.async(parse.json[Earthquake]) { implicit request: Request[Earthquake] =>
    val earthquake = request.body
    val response = dataService.createEarthquake(earthquake)
    response.map(res => Ok(res.toString))
  }

  def read(id:String)= Action.async { implicit request: Request[AnyContent] =>
    val response = dataService.getEarthquake(id)
    response.map(res =>  if(res.isDefined) Ok(views.html.index()) else Ok("Earthquake not found"))
  }

  def update(id:String)= Action.async { implicit request: Request[AnyContent] =>
    val earthquake = request.body.asInstanceOf[Earthquake]
    val response = dataService.updateEarthquake(id, earthquake)
    response.map(res => Ok(res.toString))
  }

  def delete(id:String)= Action.async { implicit request: Request[AnyContent] =>
    val response = dataService.deleteEarthquake(id)
    response.map(deleted =>  if(deleted) Ok("Earthquake deleted") else Ok("Earthquake not deleted"))
  }



}
