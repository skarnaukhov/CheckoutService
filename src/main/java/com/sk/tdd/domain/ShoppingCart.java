package com.sk.tdd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingCart {
  @Id
  private String id;
  @Column
  @ElementCollection(targetClass=Product.class)
  private Set<Product> productList = new LinkedHashSet<>();

  public ShoppingCart(String id) {
    this.id = id;
  }

  public int getCartValue() {
    return productList.stream().mapToInt(Product::getPrice).sum();
  }
}
