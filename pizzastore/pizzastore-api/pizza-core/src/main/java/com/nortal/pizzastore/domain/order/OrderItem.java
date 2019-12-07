package com.nortal.pizzastore.domain.order;

import com.nortal.pizzastore.domain.product.Product;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Set;

@Value(staticConstructor = "of")
public class OrderItem {
  private Set<Product> products;

  public BigDecimal getPrice() {
    return products.stream()
      .map(Product::getPrice)
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
