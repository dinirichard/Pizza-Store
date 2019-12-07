package com.nortal.pizzastore.order;

import com.nortal.pizzastore.domain.user.User;
import com.nortal.pizzastore.usecase.placeorder.PlaceOrder;

import java.util.List;

import static java.util.stream.Collectors.toList;

class OrderResourceMapper {

  static PlaceOrder.Request toRequest(OrderInputResource resource, User user) {
    return PlaceOrder.Request.builder()
      .name(resource.customer.name)
      .email(resource.customer.email)
      .phone(resource.customer.phone)
      .address(resource.customer.address)
      .pizzas(toPizzas(resource.pizzas))
      .customer(user)
      .build();
  }

  private static List<PlaceOrder.Request.Pizza> toPizzas(List<OrderInputResource.Pizza> resources) {
    return resources.stream()
      .map(OrderResourceMapper::toPizza)
      .collect(toList());
  }

  private static PlaceOrder.Request.Pizza toPizza(OrderInputResource.Pizza resource) {
    return PlaceOrder.Request.Pizza.builder()
      .baseId(resource.base)
      .toppingIds(resource.toppings)
      .build();
  }
}
