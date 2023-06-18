package services

import com.typesafe.config.Config
import models.Alerts
import sttp.client3.circe.asJson
import sttp.client3.{Identity, Response, ResponseException, UriContext, basicRequest}
import utils.SttpClient.backend

object AlertService {

  def getAlerts()(implicit config: Config): List[Alerts] = {

    val alertsResponse: Identity[Response[Either[ResponseException[String, io.circe.Error], List[Alerts]]]] =
      basicRequest
        .get(uri"https://services.prewave.ai/adminInterface/api/testAlerts?key=${config.getString("apiKey")}")
        .response(asJson[List[Alerts]])
        .send(backend)

    alertsResponse.body.getOrElse(List.empty[Alerts])
  }

}
