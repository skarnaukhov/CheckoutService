package com.sk.tdd.service;

import com.sk.tdd.domain.ShoppingCart;
import com.sk.tdd.service.integration.CheckoutResource;
import org.springframework.stereotype.Service;

@Service
public class CheckoutIntegrationService {

  private CheckoutResource checkoutResource;

  public CheckoutIntegrationService(CheckoutResource checkoutResource) {
    this.checkoutResource = checkoutResource;
  }

  public void checkoutCart(ShoppingCart cart) {
    this.checkoutResource.checkout(cart.getId(), cart.getCartValue());
  }
}
