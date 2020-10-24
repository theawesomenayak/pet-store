package com.petstore.client;

import com.petstore.model.Pet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

public final class PetStoreClient {

  private final DynamoDbClient dynamoDbClient;

  public PetStoreClient(final DynamoDbClient dynamoDbClient) {

    this.dynamoDbClient = dynamoDbClient;
  }

  public String write(final Pet pet) {

    final String id = UUID.randomUUID().toString();
    final PutItemRequest putItemRequest = PutItemRequest.builder()
        .tableName("pet-store")
        .item(
            Map.of(
                "id", AttributeValue.builder().s(id).build(),
                "name", AttributeValue.builder().s(pet.getName()).build(),
                "age", AttributeValue.builder().n(Integer.toString(pet.getAge())).build(),
                "category", AttributeValue.builder().s(pet.getCategory()).build()
            )
        )
        .build();
    dynamoDbClient.putItem(putItemRequest);
    return id;
  }

  public List<Pet> read() {

    final ScanRequest scanRequest = ScanRequest.builder()
        .tableName("pet-store")
        .build();

    final ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
    final List<Map<String, AttributeValue>> returnedItems = scanResponse.items();
    final List<Pet> pets = new ArrayList<>();
    returnedItems.forEach(item -> {
      final Pet pet = new Pet();
      pet.setId(item.get("id").s());
      pet.setName(item.get("name").s());
      pet.setAge(Integer.parseInt(item.get("age").n()));
      pet.setCategory(item.get("category").s());
      pets.add(pet);
    });
    return pets;
  }
}
