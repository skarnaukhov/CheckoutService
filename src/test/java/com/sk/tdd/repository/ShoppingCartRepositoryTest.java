package com.sk.tdd.repository;

import com.sk.tdd.domain.Product;
import com.sk.tdd.domain.ShoppingCart;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@Ignore
public class ShoppingCartRepositoryTest {

  @Autowired
  private ShoppingCartRepository repository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  public void getSample_returnShoppingCart() throws Exception {
    String id = "ID";
    ShoppingCart savedShoppingCart = testEntityManager.persistFlushFind(new ShoppingCart(id));
    ShoppingCart foundShoppingCart = repository.findById(id).get();

    assertThat(foundShoppingCart).isNotNull();
    assertThat(foundShoppingCart.getId()).isEqualTo(savedShoppingCart.getId());
  }

  @Test
  public void testAddProductToShoppingCart() throws Exception {
    String id = "ID";
    testEntityManager.persistFlushFind(new ShoppingCart(id));
    Product savedProduct = testEntityManager.persistFlushFind(new Product("productId", "other", 100));
    ShoppingCart foundShoppingCart = repository.findById(id).get();

    foundShoppingCart.getProductList().add(savedProduct);
    repository.save(foundShoppingCart);
    foundShoppingCart = repository.findById(id).get();

    assertThat(foundShoppingCart).isNotNull();
    assertThat(foundShoppingCart.getProductList()).contains(savedProduct);
  }

  @Test
  public void testRemoveProductFromShoppingCart() throws Exception {
    String id = "ID";
    String productId = "productId";

    testEntityManager.persistFlushFind(new ShoppingCart(id));
    Product savedProduct = testEntityManager.persistFlushFind(new Product(productId, "other", 100));
    ShoppingCart foundShoppingCart = repository.findById(id).get();

    foundShoppingCart.getProductList().add(savedProduct);
    repository.save(foundShoppingCart);

    final Set<Product> products = foundShoppingCart.getProductList().stream()
        .filter(product -> !product.getId().equals(productId))
        .collect(Collectors.toSet());

    repository.updateCartProducts(products);

    foundShoppingCart = repository.findById(id).get();

    assertThat(foundShoppingCart).isNotNull();
    assertThat(foundShoppingCart.getProductList()).doesNotContain(savedProduct);
  }



}
