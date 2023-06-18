package utils

import sttp.client3.HttpClientSyncBackend

object SttpClient {
  val backend = HttpClientSyncBackend()
}
