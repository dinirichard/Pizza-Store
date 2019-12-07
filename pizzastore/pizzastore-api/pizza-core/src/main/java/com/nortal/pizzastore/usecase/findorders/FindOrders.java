package com.nortal.pizzastore.usecase.findorders;

import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderGateway;
import com.nortal.pizzastore.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindOrders {

  private final OrderGateway orderGateway;

  public void execute(Request request, FindOrdersPresenter presenter) {
    List<Order> orders;
    if (request.user.isAdmin()) {
      orders = orderGateway.findOrders();
    } else {
      orders = orderGateway.findOrdersByUser(request.user);
    }
    presenter.present(orders);
  }

  @AllArgsConstructor(staticName = "of")
  public static class Request {
    public User user;
  }
}
