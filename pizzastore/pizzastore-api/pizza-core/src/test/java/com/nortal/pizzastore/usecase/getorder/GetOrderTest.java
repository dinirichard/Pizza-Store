package com.nortal.pizzastore.usecase.getorder;

import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderGateway;
import com.nortal.pizzastore.domain.user.Role;
import com.nortal.pizzastore.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
class GetOrderTest {

  @Mock
  private OrderGateway gateway;
  @Mock
  private GetOrderPresenter presenter;

  private GetOrder getOrder;

  @BeforeEach
  void setUp() {
    getOrder = new GetOrder(gateway);
  }

  @Test
  void admin_shouldFindAnyOrder() {
    // given
    User adminUser = User.builder()
      .roles(new HashSet<>(List.of(Role.ROLE_ADMIN)))
      .build();

    given(gateway.findById(0L))
      .willReturn(Optional.of(Order.builder().build()));

    // when
    getOrder.execute(GetOrder.Request.of(0L, adminUser), presenter);

    // then
    verify(gateway).findById(0L);
    verify(presenter).present(any(Order.class));
  }

  @Nested
  class Customer {

    private User customer;

    @BeforeEach
    void setUp() {
      customer = User.builder()
        .roles(new HashSet<>(List.of(Role.ROLE_CUSTOMER)))
        .build();
    }

    @Test
    void shouldFindOnlyItsOwnOrder() {
      // given
      given(gateway.findByIdAndUser(0L, customer))
        .willReturn(Optional.of(Order.builder().build()));

      // when
      getOrder.execute(GetOrder.Request.of(0L, customer), presenter);

      // then
      verify(gateway).findByIdAndUser(0L, customer);
      verify(presenter).present(any(Order.class));
    }

    @Test
    void noOrdersFound_shouldNotPresent() {
      // given
      given(gateway.findByIdAndUser(0L, customer))
        .willReturn(Optional.empty());

      // when
      getOrder.execute(GetOrder.Request.of(0L, customer), presenter);

      // then
      verify(gateway).findByIdAndUser(0L, customer);
      verify(presenter, never()).present(any(Order.class));
    }
  }

}