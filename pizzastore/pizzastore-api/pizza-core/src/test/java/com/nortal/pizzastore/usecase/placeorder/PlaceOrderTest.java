package com.nortal.pizzastore.usecase.placeorder;

import com.nortal.pizzastore.domain.order.InvalidOrder;
import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderGateway;
import com.nortal.pizzastore.domain.user.Role;
import com.nortal.pizzastore.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlaceOrderTest {

  @Mock
  private OrderGateway gateway;
  @Mock
  private OrderCreator orderCreator;
  @Mock
  private PlaceOrderPresenter presenter;

  private PlaceOrder placeOrder;
  private PlaceOrder.Request request;

  @BeforeEach
  void setUp() {
    placeOrder = new PlaceOrder(gateway, orderCreator);

    request = PlaceOrder.Request.builder()
      .name("name")
      .email("email@email.com")
      .phone("phone")
      .address("address")
      .customer(User.builder().roles(new HashSet<>(List.of(Role.ROLE_CUSTOMER))).build())
      .pizzas(List.of(PlaceOrder.Request.Pizza.builder()
        .baseId(1L)
        .toppingIds(List.of(2L, 3L))
        .build()))
      .build();
  }

  @Test
  void successfulOrder() {
    // given
    given(orderCreator.create(request))
      .willReturn(Order.builder().build());

    given(gateway.save(any(Order.class)))
      .willReturn(0L);

    var responseCaptor = ArgumentCaptor.forClass(PlaceOrder.Response.class);

    // when
    placeOrder.execute(request, presenter);

    // then
    verify(gateway).save(any(Order.class));
    verify(presenter).success(responseCaptor.capture());
    PlaceOrder.Response response = responseCaptor.getValue();
    assertThat(response.orderId).isEqualTo(0L);
  }

  @Test
  void invalidOrder() {
    // given
    given(orderCreator.create(request))
      .willThrow(new InvalidOrder("fail"));

    // when
    placeOrder.execute(request, presenter);

    // then
    verify(gateway, never()).save(any(Order.class));
    verify(presenter).failure("Invalid order: fail");
  }
}
