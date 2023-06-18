import better.files._
import services.{AlertService, QueryTermService}
import com.typesafe.config.ConfigFactory

object AlertExtraction {
  def main(args: Array[String]): Unit = {

    implicit val config = ConfigFactory.load()

    val alerts = AlertService.getAlerts()
    val queryTerms = QueryTermService.getQueryTerms()

    val results = scala.collection.mutable.Map[String, List[Long]]()
    alerts.map{ alert =>
      queryTerms.map{ queryTerm =>

        val queryTokens = queryTerm.text.split("\\s")

        // Filter the alert content with the query term's language.
        alert.contents.filter(_.language.equals(queryTerm.language)).map{ alertContent =>

          //if the keepOrder is true and query term have multiple tokens the use regex.
          if(queryTerm.keepOrder && queryTokens.length > 1){
            val query = queryTokens.mkString("\\s")
            val regex = s"(.*\\b$query\\b.*)".r
            if(regex.matches(alertContent.text)){
              results += (alert.id -> results.getOrElse(alert.id,List[Long]()).appended(queryTerm.id).distinct)
            }
          }else{
            if(queryTokens.forall(alertContent.text.contains(_))){
              results += (alert.id -> results.getOrElse(alert.id,List[Long]()).appended(queryTerm.id).distinct)
            }
          }



        }
      }
    }

    //println(results)


    val file = File("data.txt")


    //check if the data file exist the append all existing data to the map
    if (file.exists) {
      file.lines.foreach{ line =>
        val keyValue = line.trim().split(":")
        if (keyValue.length == 2) {
          val key = keyValue(0).trim()
          val values = keyValue(1).trim().split(",").toList.flatMap { value =>
            scala.util.Try(value.trim().toLong).toOption
          }
          results += (key -> results.getOrElse(key,List[Long]()).appendedAll(values).distinct)
        }
      }
    }

    // Clear the older file and write all data
    file.clear()
    results.foreach { case (key, values) =>
      val line = s"$key: ${values.mkString(", ")}"
      file.appendLine(line)
    }

    println("Matches written to data.txt")


  }
}