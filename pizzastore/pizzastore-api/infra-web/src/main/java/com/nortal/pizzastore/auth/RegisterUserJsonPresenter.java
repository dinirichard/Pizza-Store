package com.nortal.pizzastore.auth;

import com.nortal.pizzastore.usecase.registeruser.RegisterUserPresenter;

class RegisterUserJsonPresenter implements RegisterUserPresenter {

  String reason;

  @Override
  public void failure(String reason) {
    this.reason = reason;
  }

  boolean isSuccessful() {
    return reason == null;
  }
}
