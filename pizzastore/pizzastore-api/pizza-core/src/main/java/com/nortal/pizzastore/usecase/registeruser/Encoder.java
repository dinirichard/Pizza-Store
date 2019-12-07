package com.nortal.pizzastore.usecase.registeruser;

public interface Encoder {

  String encode(CharSequence rawPassword);
}
