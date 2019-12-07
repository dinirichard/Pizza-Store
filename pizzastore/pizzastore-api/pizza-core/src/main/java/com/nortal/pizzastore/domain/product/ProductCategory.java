package com.nortal.pizzastore.domain.product;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProductCategory {
  private String code;
}
