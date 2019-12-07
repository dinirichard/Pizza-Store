package com.nortal.pizzastore.usecase.findorders;

import com.nortal.pizzastore.domain.order.OrderGateway;
import com.nortal.pizzastore.domain.user.Role;
import com.nortal.pizzastore.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FindOrdersTest {

  @Mock
  private OrderGateway gateway;
  @Mock
  private FindOrdersPresenter presenter;

  private FindOrders findOrders;

  @BeforeEach
  void setUp() {
    findOrders = new FindOrders(gateway);
  }

  @Test
  void admin_shouldFindAllOrders() {
    // given
    User adminUser = User.builder()
      .roles(new HashSet<>(List.of(Role.ROLE_ADMIN)))
      .build();

    // when
    findOrders.execute(FindOrders.Request.of(adminUser), presenter);

    // then
    verify(gateway).findOrders();
    verify(presenter).present(anyList());
  }

  @Test
  void customer_shouldFindOnlyItsOwnOrders() {
    // given
    User customer = User.builder()
      .roles(new HashSet<>(List.of(Role.ROLE_CUSTOMER)))
      .build();

    // when
    findOrders.execute(FindOrders.Request.of(customer), presenter);

    // then
    verify(gateway).findOrdersByUser(customer);
    verify(presenter).present(anyList());
  }
}