package com.nortal.pizzastore.order;

import com.nortal.pizzastore.usecase.placeorder.PlaceOrder;
import com.nortal.pizzastore.usecase.placeorder.PlaceOrderPresenter;

class PlaceOrderResultPresenter implements PlaceOrderPresenter {

  Long orderId;
  String failureReason;

  @Override
  public void success(PlaceOrder.Response response) {
    orderId = response.orderId;
  }

  @Override
  public void failure(String reason) {
    failureReason = reason;
  }

  boolean isSuccessful() {
    return orderId != null;
  }
}
