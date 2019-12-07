package com.nortal.pizzastore.usecase.cancelorder;

import com.nortal.pizzastore.domain.order.OrderGateway;
import com.nortal.pizzastore.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelOrder {

  private final OrderGateway gateway;

  public void execute(Request request, CancelOrderPresenter presenter) {

    if (!request.user.isAdmin()) {
      presenter.failure("Order can only be cancelled by admin");
      return;
    }
    gateway.findById(request.orderId)
      .ifPresentOrElse(gateway::cancel,
        () -> presenter.failure(String.format("Order with id '%d' does not exist", request.orderId)));
  }

  @AllArgsConstructor(staticName = "of")
  public static class Request {
    public Long orderId;
    public User user;
  }
}
