package com.nortal.pizzastore.order;

import com.nortal.pizzastore.domain.order.Customer;
import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderItem;
import com.nortal.pizzastore.product.ProductEntityMapper;
import com.nortal.pizzastore.user.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
class OrderEntityMapper {

  private final UserEntityMapper userEntityMapper;

  Order toDomainEntity(OrderEntity entity) {
    return Order.builder()
      .id(entity.getId())
      .customer(toCustomer(entity))
      .created(entity.getCreated())
      .items(toOrderItems(entity.getItems()))
      .build();
  }

  private Customer toCustomer(OrderEntity entity) {
    return Customer.builder()
      .user(userEntityMapper.toDomainEntity(entity.getCustomer()))
      .name(entity.getName())
      .email(entity.getEmail())
      .phone(entity.getPhone())
      .address(entity.getAddress())
      .build();
  }

  private List<OrderItem> toOrderItems(Set<OrderItemEntity> items) {
    return items.stream()
      .map(this::toOrderItem)
      .collect(toList());
  }

  private OrderItem toOrderItem(OrderItemEntity entity) {
    return OrderItem.of(entity.getProducts().stream()
      .map(ProductEntityMapper::toDomainEntity)
      .collect(Collectors.toSet()));
  }
}
