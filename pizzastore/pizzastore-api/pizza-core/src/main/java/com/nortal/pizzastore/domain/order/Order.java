package com.nortal.pizzastore.domain.order;

import com.nortal.pizzastore.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Setter
@Getter
@Builder
public class Order {
  private Long id;
  private Customer customer;
  @Builder.Default
  private LocalDateTime created = LocalDateTime.now();
  @Builder.Default
  private List<OrderItem> items = new ArrayList<>();

  public BigDecimal getTotalPrice() {

    BigDecimal totalPrice = BigDecimal.ZERO;

    for(OrderItem item : items){
      totalPrice = totalPrice.add(item.getPrice());
    }

    // TODO! Calculate totalprice ...DONE
    return totalPrice;
  }
}
