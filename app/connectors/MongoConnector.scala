package connectors

import javax.inject.Inject
import repositories.EarthquakeRepository

class MongoDbConnectorImpl @Inject()(val earthquakeRepository: EarthquakeRepository) extends MongoDbConnector {

}

trait MongoDbConnector{

}
