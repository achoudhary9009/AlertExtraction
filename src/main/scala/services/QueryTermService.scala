package services

import com.typesafe.config.Config
import io.circe.generic.auto._
import models.QueryTerm
import sttp.client3.circe.asJson
import sttp.client3.{Identity, Response, ResponseException, UriContext, basicRequest}
import utils.SttpClient.backend

object QueryTermService {

  def getQueryTerms()(implicit config: Config): List[QueryTerm] = {

    val queryResponse: Identity[Response[Either[ResponseException[String, io.circe.Error], List[QueryTerm]]]] =
      basicRequest
        .get(uri"https://services.prewave.ai/adminInterface/api/testQueryTerm?key=${config.getString("apiKey")}")
        .response(asJson[List[QueryTerm]])
        .send(backend)

    queryResponse.body.getOrElse(List.empty[QueryTerm])
  }

}
