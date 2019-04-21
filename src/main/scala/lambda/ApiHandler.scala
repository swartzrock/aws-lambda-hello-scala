package lambda


import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent

import scala.collection.JavaConverters._

/**
 * Scala entrypoint for the API Gateway Lambda function
  */
object ApiHandler {

  /**
    * Handle a Lambda request indirectly via the API Gateway
    * @param request the Java HTTP request
    * @param context the Java Lambda context
    * @return the HTTP response
    */
  def handle(request: APIGatewayProxyRequestEvent, context: Context): Response = {
    
    println("handling %s %s, remaining time is %d ms".format(
      request.getHttpMethod, request.getPath, context.getRemainingTimeInMillis) )
    
    println(s"""environment = ${sys.env.getOrElse("env", "n/a")}""")

    val name = request.getPathParameters.get("name")
    Response(s"Hello, $name\n", Map("Content-Type" -> "text/plain"))
  }
  
  case class Response(body: String, headers: Map[String,String], statusCode: Int = 200) {
    def javaHeaders: java.util.Map[String, String] = headers.asJava
  }

}

