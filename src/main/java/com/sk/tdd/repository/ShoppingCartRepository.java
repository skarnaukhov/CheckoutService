package com.sk.tdd.repository;

import com.sk.tdd.domain.Product;
import com.sk.tdd.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {
  void updateCartProducts(Set<Product> productSet);
}
