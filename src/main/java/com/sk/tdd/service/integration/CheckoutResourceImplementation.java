package com.sk.tdd.service.integration;

import com.sk.tdd.domain.CheckoutResult;
import org.springframework.stereotype.Service;

@Service
public class CheckoutResourceImplementation implements CheckoutResource {
  @Override
  public CheckoutResult checkout(String cartId, int cartValue) {
    return new CheckoutResult(true);
  }
}
