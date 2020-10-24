package com.petstore;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.petstore.model.Pet;
import com.petstore.utils.DependencyModule;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class App implements
    RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
      final Context context) {

    final Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("X-Custom-Header", "application/json");

    final APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
        .withHeaders(headers);
    try {
      final String httpMethod = input.getHttpMethod();
      if ("GET".equalsIgnoreCase(httpMethod)) {
        final List<Pet> pets = DependencyModule.PET_STORE_CLIENT.read();
        final String result = DependencyModule.OBJECT_MAPPER.writeValueAsString(pets);
        return response.withStatusCode(200).withBody(result);
      } else {
        final Pet pet = DependencyModule.OBJECT_MAPPER.readValue(input.getBody(), Pet.class);
        final String id = DependencyModule.PET_STORE_CLIENT.write(pet);
        return response.withStatusCode(200).withBody(id);
      }
    } catch (final IOException e) {
      return response.withStatusCode(500).withBody("{}");
    }
  }
}
