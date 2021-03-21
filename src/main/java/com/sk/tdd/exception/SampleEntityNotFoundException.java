package com.sk.tdd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SampleEntityNotFoundException extends RuntimeException {
  public SampleEntityNotFoundException(String message) {
    super(message);
  }
}
