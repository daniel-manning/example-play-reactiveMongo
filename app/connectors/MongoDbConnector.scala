package connectors

import com.google.inject.ImplementedBy
import javax.inject.Inject
import models.Earthquake
import play.api.libs.json.Json
import repositories.EarthquakeRepository
import reactivemongo.play.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class MongoDbConnectorImpl @Inject()(val earthquakeRepository: EarthquakeRepository) extends MongoDbConnector {
  override def save(earthquake: Earthquake): Future[Earthquake] = earthquakeRepository().insert(earthquake).map(wr => {println(wr); earthquake})

  override def fetch(id: String): Future[Option[Earthquake]] = earthquakeRepository().collection.find(Json.obj("id" -> id)).one[Earthquake]

  override def update(id: String, updatedEarthquake: Earthquake): Future[Earthquake] = earthquakeRepository().collection.update(Json.obj("id" -> id), updatedEarthquake, upsert=true).map(uwr => {println(uwr); updatedEarthquake})

  override def destroy(id: String): Future[Boolean] = earthquakeRepository().collection.remove(Json.obj("id" -> id)).map(wr => {println(wr); true})
}


@ImplementedBy(classOf[MongoDbConnectorImpl])
trait MongoDbConnector{
  def save(value:Earthquake):Future[Earthquake]
  def fetch(id:String):Future[Option[Earthquake]]
  def update(id:String, updatedEarthquake:Earthquake):Future[Earthquake]
  def destroy(id:String):Future[Boolean]
}
