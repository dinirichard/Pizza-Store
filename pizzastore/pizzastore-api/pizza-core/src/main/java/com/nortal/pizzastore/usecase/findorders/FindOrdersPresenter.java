package com.nortal.pizzastore.usecase.findorders;

import com.nortal.pizzastore.domain.order.Order;

import java.util.List;

public interface FindOrdersPresenter {

  void present(List<Order> orders);
}
