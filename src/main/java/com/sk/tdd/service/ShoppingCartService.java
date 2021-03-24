package com.sk.tdd.service;

import com.sk.tdd.domain.Product;
import com.sk.tdd.domain.ShoppingCart;
import com.sk.tdd.exception.ProductNotFoundException;
import com.sk.tdd.exception.ShopCartNotFoundException;
import com.sk.tdd.repository.ProductRepository;
import com.sk.tdd.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

  private final ShoppingCartRepository shoppingCartRepository;
  private final ProductRepository productRepository;
  private final CheckoutIntegrationService integrationService;

  public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, CheckoutIntegrationService checkoutIntegrationService) {
    this.shoppingCartRepository = shoppingCartRepository;
    this.productRepository = productRepository;
    this.integrationService = checkoutIntegrationService;
  }

  public ShoppingCart getShoppingCart(String id) {
    return shoppingCartRepository.findById(id).orElseThrow(ShopCartNotFoundException::new);
  }

  public void addProductToCart(String cartId, String productId) {
    final ShoppingCart cart = getShoppingCart(cartId);
    cart.getProductList().add(getProduct(productId));
    shoppingCartRepository.save(cart);
  }

  private Product getProduct(String id) {
    return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
  }

  public void checkout(String cartId) {
    final ShoppingCart cart = getShoppingCart(cartId);
    this.integrationService.checkoutCart(cart);
  }

  public void removeProductFromCart(String cartId, String productId) {
    final ShoppingCart cart = getShoppingCart(cartId);
    final Set<Product> products = cart.getProductList().stream()
        .filter(product -> !product.getId().equals(productId))
        .collect(Collectors.toSet());
//    shoppingCartRepository.updateCartProducts(products);
  }
}
