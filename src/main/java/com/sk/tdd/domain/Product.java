package com.sk.tdd.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
public class Product {
  @Id
  private String id;
  private String name;
  private int price;

  @Builder
  public Product(String id, String name, int price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }
}
