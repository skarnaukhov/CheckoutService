package com.sk.tdd;

import com.sk.tdd.domain.Product;
import com.sk.tdd.domain.ShoppingCart;
import com.sk.tdd.repository.ProductRepository;
import com.sk.tdd.repository.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class ShoppingCartIntegrationTest {

  private static final String CART_ID_1 = "CART_ID_1";
  private static final String PRODUCT_ID_1 = "PRODUCT_ID_1";
  private static final String PRODUCT_NAME_1 = "PRODUCT_NAME_1";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private ShoppingCartRepository shoppingCartRepository;
  @Autowired
  private ProductRepository productRepository;

  @Before
  public void setUp() throws Exception {
    shoppingCartRepository.save(new ShoppingCart(CART_ID_1));
    productRepository.save(new Product(PRODUCT_ID_1, PRODUCT_NAME_1, 100));
  }

  @Test
  public void testGetCartWithProducts() throws Exception {
    ShoppingCart shoppingCart = webTestClient.get().uri("/shoppingCart/{id}", CART_ID_1)
        .exchange().expectStatus().isOk()
        .expectBody(ShoppingCart.class).returnResult().getResponseBody();

    assertThat(shoppingCart.getId()).isEqualTo(CART_ID_1);
  }

  @Test
  public void testProductAddedToCart() throws Exception {
    webTestClient.put().uri("shoppingCart/" + CART_ID_1)
        .body(BodyInserters.fromFormData("productId", PRODUCT_ID_1))
        .exchange().expectStatus().isOk();

    final ShoppingCart shoppingCart = webTestClient.get().uri("/shoppingCart/{id}", CART_ID_1)
        .exchange().expectStatus().isOk()
        .expectBody(ShoppingCart.class).returnResult().getResponseBody();

    assertThat(shoppingCart.getProductList()).isNotNull();
    assertThat(shoppingCart.getProductList()).contains(new Product(PRODUCT_ID_1, PRODUCT_NAME_1, 100));
  }

  @Test
  public void testProductRemovedFromCart() throws Exception {
    webTestClient.delete().uri("shoppingCart/" + CART_ID_1 + "/product/" + PRODUCT_ID_1)
        .exchange().expectStatus().isOk();

    final ShoppingCart shoppingCart = webTestClient.get().uri("/shoppingCart/{id}", CART_ID_1)
        .exchange().expectStatus().isOk()
        .expectBody(ShoppingCart.class).returnResult().getResponseBody();

    assertThat(shoppingCart.getProductList()).isNotNull();
    assertThat(shoppingCart.getProductList()).doesNotContain(new Product(PRODUCT_ID_1, PRODUCT_NAME_1, 100));
  }

}
