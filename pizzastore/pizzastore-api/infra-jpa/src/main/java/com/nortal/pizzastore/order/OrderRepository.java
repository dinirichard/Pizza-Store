package com.nortal.pizzastore.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  List<OrderEntity> findByCustomerUsernameOrderByCreatedDesc(String username);
}
