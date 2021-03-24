package com.sk.tdd.repository;

import com.sk.tdd.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {
//  void updateCartProducts(Set<Product> productSet);
}
