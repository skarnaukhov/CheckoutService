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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class CheckoutIntegrationTest {

  private static final String CART_ID_1 = "CART_ID_1";
  private static final String PRODUCT_ID_1 = "PRODUCT_ID_1";
  private static final String PRODUCT_ID_2 = "PRODUCT_ID_2";
  private static final String PRODUCT_NAME_1 = "PRODUCT_NAME_1";
  private static final String PRODUCT_NAME_2 = "PRODUCT_NAME_2";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private ShoppingCartRepository shoppingCartRepository;
  @Autowired
  private ProductRepository productRepository;

  @Before
  public void setUp() throws Exception {
    shoppingCartRepository.save(new ShoppingCart(CART_ID_1));
    productRepository.save(new Product(PRODUCT_ID_1, PRODUCT_NAME_1, 32));
    productRepository.save(new Product(PRODUCT_ID_2, PRODUCT_NAME_2, 77));
  }

  @Test
  public void testGetCartWithProducts() throws Exception {
    webTestClient.post().uri("/shoppingCart/{id}/checkout", CART_ID_1)
        .exchange().expectStatus().isOk();
  }

  @Test
  public void testCartValueIsCalculated() throws Exception {
    //TODO write test
//    assertThat(false).isTrue();
  }
}
