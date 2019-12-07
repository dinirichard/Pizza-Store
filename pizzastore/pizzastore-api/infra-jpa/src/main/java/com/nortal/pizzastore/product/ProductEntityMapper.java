package com.nortal.pizzastore.product;

import com.nortal.pizzastore.domain.product.Product;
import com.nortal.pizzastore.domain.product.ProductCategory;

public class ProductEntityMapper {

  public static Product toDomainEntity(ProductEntity entity) {
    return Product.builder()
      .id(entity.getId())
      .name(entity.getName())
      .category(ProductCategory.of(entity.getCategory().getCode()))
      .price(entity.getPrice())
      .build();
  }
}
