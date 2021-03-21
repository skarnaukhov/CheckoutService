package com.sk.tdd.controller;

import com.sk.tdd.domain.ShoppingCart;
import com.sk.tdd.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

  private final ShoppingCartService shoppingCartService;

  public ShoppingCartController(ShoppingCartService shoppingCartService) {
    this.shoppingCartService = shoppingCartService;
  }

  @GetMapping("/{id}")
  public ShoppingCart getShoppingCartById(@PathVariable("id") String id) {
    return shoppingCartService.getShoppingCart(id);
  }

  @PutMapping(value = "/{id}")
  public void getShoppingCartById(@PathVariable("id") final String cartId, @RequestParam("productId") final String productId) {
    shoppingCartService.addProductToCart(cartId, productId);
  }

  @PostMapping("/{id}/checkout")
  public void checkoutCart(@PathVariable("id") final String cartId) {
    shoppingCartService.checkout(cartId);
  }

  @DeleteMapping("/{cartId}/productId/{productId}")
  public void removeProductFromCart(@PathVariable("cartId") final String cartId, @PathVariable("productId") final String productId) {
    shoppingCartService.removeProductFromCart(cartId, productId);
  }

}
