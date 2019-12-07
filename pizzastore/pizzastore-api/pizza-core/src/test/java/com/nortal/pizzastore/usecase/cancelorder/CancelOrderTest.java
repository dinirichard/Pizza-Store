package com.nortal.pizzastore.usecase.cancelorder;

import com.nortal.pizzastore.domain.order.Order;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CancelOrderTest {

  @Mock
  private OrderGateway gateway;
  @Mock
  private CancelOrderPresenter presenter;

  private CancelOrder cancelOrder;

  @BeforeEach
  void setUp() {
    cancelOrder = new CancelOrder(gateway);
  }

  @Test
  void nonAdmin_shouldNotBeAbleToCancelOrder() {
    // given
    User nonAdminUser = User.builder()
      .roles(new HashSet<>(List.of(Role.ROLE_CUSTOMER)))
      .build();

    // when
    cancelOrder.execute(CancelOrder.Request.of(0L, nonAdminUser), presenter);

    // then
    verify(gateway, never()).cancel(any(Order.class));
    verify(presenter).failure("Order can only be cancelled by admin");
  }

  @Test
  void orderNotFound() {
    // given
    User adminUser = User.builder()
      .roles(new HashSet<>(List.of(Role.ROLE_ADMIN)))
      .build();

    given(gateway.findById(0L))
      .willReturn(Optional.empty());

    // when
    cancelOrder.execute(CancelOrder.Request.of(0L, adminUser), presenter);

    // then
    verify(gateway, never()).cancel(any(Order.class));
    verify(presenter).failure("Order with id '0' does not exist");
  }

  @Test
  void successful() {
    // given
    User adminUser = User.builder()
      .roles(new HashSet<>(List.of(Role.ROLE_ADMIN)))
      .build();

    given(gateway.findById(0L))
      .willReturn(Optional.of(Order.builder().build()));

    // when
    cancelOrder.execute(CancelOrder.Request.of(0L, adminUser), presenter);

    // then
    verify(gateway).cancel(any(Order.class));
  }
}