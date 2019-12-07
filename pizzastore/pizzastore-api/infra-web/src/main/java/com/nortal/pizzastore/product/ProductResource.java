package com.nortal.pizzastore.product;

import com.nortal.pizzastore.domain.product.Product;
import lombok.Getter;

@Getter
public class ProductResource {

  private Long id;
  private String name;
  private String categoryCode;
  private Double price;

  public ProductResource(Product product) {
    id = product.getId();
    name = product.getName();
    categoryCode = product.getCategoryCode();
    price = product.getPrice().doubleValue();
  }
}
