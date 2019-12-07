package com.nortal.pizzastore.order;

import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderItem;
import com.nortal.pizzastore.product.ProductResource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

class OrderOutputResource {
  public Long id;
  public LocalDateTime created;
  public CustomerResource customer;
  public List<Pizza> pizzas;
  public BigDecimal totalPrice;

  OrderOutputResource(Order order) {
    id = order.getId();
    created = order.getCreated();
    customer = new CustomerResource(order.getCustomer());
    pizzas = order.getItems().stream()
      .map(Pizza::new)
      .collect(toList());
    totalPrice = order.getTotalPrice();
  }

  class Pizza {
    public ProductResource base;
    public List<ProductResource> toppings;
    public BigDecimal price;

    public Pizza(OrderItem orderItem) {
      base = getBase(orderItem);
      toppings = getToppings(orderItem);
      price = orderItem.getPrice();
    }

    private ProductResource getBase(OrderItem orderItem) {
      return new ProductResource(orderItem.getProducts().stream()
        .filter(product -> "PIZZA_BASE".equals(product.getCategoryCode()))
        .findFirst()
        .orElseThrow(PizzaBaseNotFound::new));
    }

    private List<ProductResource> getToppings(OrderItem orderItem) {
      return orderItem.getProducts().stream()
        .filter(product -> "PIZZA_TOPPING".equals(product.getCategoryCode()))
        .map(ProductResource::new)
        .collect(toList());
    }
  }

  private class PizzaBaseNotFound extends RuntimeException {
    PizzaBaseNotFound() {
      super("Pizza base not found");
    }
  }
}
