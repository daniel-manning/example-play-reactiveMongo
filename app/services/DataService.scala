package services

import connectors.MongoDbConnector
import javax.inject.Inject
import models.Earthquake

import scala.concurrent.Future

class DataService @Inject()(mongoDbConnector: MongoDbConnector){

  def createEarthquake(earthquake:Earthquake):Future[Earthquake] = {
    mongoDbConnector.save(earthquake)
  }

  def getEarthquake(id:String):Future[Option[Earthquake]] = {
    mongoDbConnector.fetch(id)
  }

  def updateEarthquake(id:String, earthquake: Earthquake):Future[Earthquake] = {
    mongoDbConnector.update(id, earthquake)
  }

  def deleteEarthquake(id:String):Future[Boolean] = {
    mongoDbConnector.destroy(id)
  }


}
