package com.sk.tdd.service;

import com.sk.tdd.CheckoutIntegrationTest;
import com.sk.tdd.domain.CheckoutResult;
import com.sk.tdd.domain.Product;
import com.sk.tdd.domain.ShoppingCart;
import com.sk.tdd.exception.ProductNotFoundException;
import com.sk.tdd.exception.ShopCartNotFoundException;
import com.sk.tdd.repository.ProductRepository;
import com.sk.tdd.repository.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {

  @Mock
  private ShoppingCartRepository shoppingCartRepository;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private CheckoutIntegrationService checkoutIntegrationService;

  private ShoppingCartService shoppingCartService;

  @Before
  public void setUp() throws Exception {
    shoppingCartService = new ShoppingCartService(shoppingCartRepository, productRepository, checkoutIntegrationService);
  }

  @Test
  public void testGetShoppingCartByID() throws Exception {
    String id = "ID";
    given(shoppingCartRepository.findById(id)).willReturn(Optional.of(new ShoppingCart(id)));
    ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(id);

    assertThat(shoppingCart).isNotNull();
    assertThat(shoppingCart.getId()).isEqualTo(id);
    verify(shoppingCartRepository, times(1)).findById(id);
  }

  @Test(expected = ShopCartNotFoundException.class)
  public void testGetShoppingCartThrowsExceptionWhenNotFound() throws Exception {
    given(shoppingCartRepository.findById(anyString())).willReturn(Optional.empty());
    shoppingCartService.getShoppingCart("ID");
  }


  @Test
  public void testProductAddedToCart() throws Exception {
    ShoppingCart cart = new ShoppingCart("cartID");
    Product product = new Product("productId", "other", 100);
    given(shoppingCartRepository.findById(cart.getId())).willReturn(Optional.of(cart));
    given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
    given(shoppingCartRepository.save(cart)).willReturn(cart);

    shoppingCartService.addProductToCart(cart.getId(), product.getId());

    verify(shoppingCartRepository, times(1)).findById(cart.getId());
    assertThat(cart.getProductList().contains(product)).isTrue();
    verify(shoppingCartRepository, times(1)).save(cart);
  }

  @Test(expected = ProductNotFoundException.class)
  public void testProductAddToCart_productNotFound() throws Exception {
    given(shoppingCartRepository.findById(anyString())).willReturn(Optional.of(new ShoppingCart()));
    given(productRepository.findById(anyString())).willReturn(Optional.empty());

    shoppingCartService.addProductToCart("cartId", "productId");
  }

  @Test
  public void testCartCheckoutSuccess() throws Exception {
    ShoppingCart cart = new ShoppingCart("cartID");
    Product product1 = new Product("productId1", "other", 33);
    Product product2 = new Product("productId2", "other", 100);
    cart.getProductList().add(product1);
    cart.getProductList().add(product2);
    given(shoppingCartRepository.findById(cart.getId())).willReturn(Optional.of(cart));

    shoppingCartService.checkout(cart.getId());

    verify(shoppingCartRepository, times(1)).findById(cart.getId());
    verify(checkoutIntegrationService, times(1)).checkoutCart(cart);
  }

  @Test
  public void testCartCheckoutThrowsCartNotFound() throws Exception {
    //TODO
    assertThat(false).isTrue();
  }

  @Test
  public void testCartCheckoutThrowsErrorOnIntegrationFailure() throws Exception {
    //TODO
    assertThat(false).isTrue();
  }

  @Test
  public void testProductRemovedFromCart() throws Exception {
    ShoppingCart cart = new ShoppingCart("cartID");
    Product product = new Product("productId", "other", 100);
    given(shoppingCartRepository.findById(cart.getId())).willReturn(Optional.of(cart));
    Set<Product> productSet = cart.getProductList();
    productSet.remove(product);

    shoppingCartService.removeProductFromCart(cart.getId(), product.getId());

    verify(shoppingCartRepository, times(1)).findById(cart.getId());
    verify(shoppingCartRepository, times(1)).updateCartProducts(productSet);

    assertThat(cart.getProductList().contains(product)).isFalse();
  }

  @Test
  public void testProductRemovalFailsOnDeleteException() throws Exception {
    //TODO
    assertThat(false).isTrue();
  }

}
