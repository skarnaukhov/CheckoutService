package com.sk.tdd.service.integration;

import com.sk.tdd.domain.CheckoutResult;

public interface CheckoutResource {
  CheckoutResult checkout(String cartId, int cartValue);
}
