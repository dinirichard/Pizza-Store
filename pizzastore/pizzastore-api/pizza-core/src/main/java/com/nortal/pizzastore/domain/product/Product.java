package com.nortal.pizzastore.domain.product;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Product {

  private Long id;
  private String name;
  private BigDecimal price;
  private ProductCategory category;

  public String getCategoryCode() {
    return category.getCode();
  }
}
