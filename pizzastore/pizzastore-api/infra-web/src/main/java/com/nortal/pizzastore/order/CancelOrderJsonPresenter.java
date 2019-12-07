package com.nortal.pizzastore.order;

import com.nortal.pizzastore.usecase.cancelorder.CancelOrderPresenter;

class CancelOrderJsonPresenter implements CancelOrderPresenter {

  String failureReason;

  @Override
  public void failure(String reason) {
    failureReason = reason;
  }

  boolean isSuccessful() {
    return failureReason == null;
  }
}
