package com.nortal.pizzastore.auth;

import lombok.Value;

@Value(staticConstructor = "of")
class UserToken {
  public String username;
  public String token;
}
