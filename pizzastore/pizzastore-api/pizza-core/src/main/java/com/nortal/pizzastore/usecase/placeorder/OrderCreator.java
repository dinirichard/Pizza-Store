package com.nortal.pizzastore.usecase.placeorder;

import com.nortal.pizzastore.domain.order.Customer;
import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
class OrderCreator {

  private final PizzaConverter pizzaConverter;

  Order create(PlaceOrder.Request request) {
    return Order.builder()
      .customer(toCustomer(request))
      .items(toOrderItems(request.pizzas))
      .build();
  }

  private Customer toCustomer(PlaceOrder.Request request) {
    return Customer.builder()
      .user(request.customer)
      .name(request.name)
      .email(request.email)
      .phone(request.phone)
      .address(request.address)
      .build();
  }

  private List<OrderItem> toOrderItems(List<PlaceOrder.Request.Pizza> pizzas) {
    return pizzas.stream()
      .map(this::toOrderItem)
      .collect(toList());
  }

  private OrderItem toOrderItem(PlaceOrder.Request.Pizza pizza) {
    return OrderItem.of(pizzaConverter.toProducts(pizza));
  }
}
