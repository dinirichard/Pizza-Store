package com.nortal.pizzastore.usecase.placeorder;

public interface PlaceOrderPresenter {

  void success(PlaceOrder.Response response);

  void failure(String reason);
}
