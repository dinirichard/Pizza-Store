package com.nortal.pizzastore.order;

import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.usecase.getorder.GetOrderPresenter;

class GetOrderJsonPresenter implements GetOrderPresenter {

  OrderOutputResource result;

  @Override
  public void present(Order order) {
    result = new OrderOutputResource(order);
  }
}
