package com.nortal.pizzastore.domain.order;

import com.nortal.pizzastore.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {

  List<Order> findOrders();

  List<Order> findOrdersByUser(User user);

  Optional<Order> findById(Long id);

  Optional<Order> findByIdAndUser(Long id, User user);

  Long save(Order order);

  void cancel(Order order);
}
