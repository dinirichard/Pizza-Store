package com.nortal.pizzastore.order;

import java.util.ArrayList;
import java.util.List;

class OrderInputResource {
  public CustomerResource customer = new CustomerResource();
  public List<Pizza> pizzas = new ArrayList<>();

  static class Pizza {
    public Long base;
    public List<Long> toppings;
  }
}
