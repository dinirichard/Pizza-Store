package com.nortal.pizzastore.usecase.getorder;

import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderGateway;
import com.nortal.pizzastore.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetOrder {

  private final OrderGateway gateway;

  public void execute(Request request, GetOrderPresenter presenter) {
    Optional<Order> order;

    if (request.user.isAdmin()) {
      order = gateway.findById(request.orderId);
    } else {
      order = gateway.findByIdAndUser(request.orderId, request.user);
    }

    order.ifPresent(presenter::present);
  }

  @AllArgsConstructor(staticName = "of")
  public static class Request {
    public Long orderId;
    public User user;
  }
}
