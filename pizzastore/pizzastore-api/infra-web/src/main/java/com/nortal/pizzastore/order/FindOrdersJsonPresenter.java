package com.nortal.pizzastore.order;

import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.usecase.findorders.FindOrdersPresenter;

import java.util.List;
import java.util.stream.Collectors;

class FindOrdersJsonPresenter implements FindOrdersPresenter {

  List<OrderOutputResource> result;

  @Override
  public void present(List<Order> orders) {
    result = orders.stream()
      .map(OrderOutputResource::new)
      .collect(Collectors.toList());
  }
}
