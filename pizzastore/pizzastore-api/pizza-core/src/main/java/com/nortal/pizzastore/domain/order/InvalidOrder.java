package com.nortal.pizzastore.domain.order;

public class InvalidOrder extends RuntimeException {
  public InvalidOrder(String message) {
    super("Invalid order: " + message);
  }
}
