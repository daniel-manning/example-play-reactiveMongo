package repositories

import javax.inject.{Inject, Singleton}
import models.Earthquake
import play.api.Configuration
import play.modules.reactivemongo.MongoDbConnection
import reactivemongo.api.DefaultDB
import reactivemongo.bson.BSONObjectID
import uk.gov.hmrc.mongo.ReactiveRepository



class ReactiveMongoRepository(config: Configuration, mongo: () => DefaultDB)
  extends ReactiveRepository[Earthquake, BSONObjectID](config.getString("appName").get, mongo, Earthquake.format)

@Singleton
class EarthquakeRepository @Inject()(config: Configuration) {

  class DbConnection extends MongoDbConnection

  private lazy val earthquakeRepository = new ReactiveMongoRepository(config, new DbConnection().db)

  def apply(): ReactiveMongoRepository = earthquakeRepository
}