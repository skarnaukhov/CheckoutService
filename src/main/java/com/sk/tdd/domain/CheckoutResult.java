package com.sk.tdd.domain;

import lombok.Data;

@Data
public class CheckoutResult {
  private boolean success;

  public CheckoutResult(boolean success) {
    this.success = success;
  }
}

