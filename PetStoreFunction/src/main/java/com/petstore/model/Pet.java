package com.petstore.model;

public final class Pet {

  private String id;
  private String name;
  private int age;
  private String category;

  public String getId() {

    return id;
  }

  public void setId(final String id) {

    this.id = id;
  }

  public String getName() {

    return name;
  }

  public void setName(final String name) {

    this.name = name;
  }

  public int getAge() {

    return age;
  }

  public void setAge(final int age) {

    this.age = age;
  }

  public String getCategory() {

    return category;
  }

  public void setCategory(final String category) {

    this.category = category;
  }
}
