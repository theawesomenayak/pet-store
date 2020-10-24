package com.petstore.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.client.PetStoreClient;
import java.net.URI;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public final class DependencyModule {

  private static final DynamoDbClient DYNAMO_DB_CLIENT = dynamoDbClient();
  public static final PetStoreClient PET_STORE_CLIENT = new PetStoreClient(DYNAMO_DB_CLIENT);
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static DynamoDbClient dynamoDbClient() {

    return DynamoDbClient.builder()
        .endpointOverride(URI.create("http://localhost:4566"))
        .region(Region.US_WEST_1)
        .httpClient(ApacheHttpClient.builder().build())
        .build();
  }
}
