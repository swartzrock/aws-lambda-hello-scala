package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * Java entrypoint for the API Gateway Lambda function
 */
public class ApiGatewayProxyHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    /**
     * Handle a Lambda request via the API Gateway
     * @param requestEvent the HTTP request
     * @param context info about this lambda function & invocation
     * @return an HTTP response
     */
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent 
        requestEvent, Context context) {

        ApiHandler.Response response = ApiHandler.handle(requestEvent, context);
        
        return new APIGatewayProxyResponseEvent()
            .withBody(response.body())
            .withStatusCode(response.statusCode())
            .withHeaders(response.javaHeaders());
    }
}
