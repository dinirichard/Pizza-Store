package com.nortal.pizzastore.usecase.placeorder;

import com.nortal.pizzastore.domain.order.InvalidOrder;
import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderGateway;
import com.nortal.pizzastore.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaceOrder {

  private final OrderGateway gateway;
  private final OrderCreator orderCreator;

  public void execute(Request request, PlaceOrderPresenter presenter) {
    try {
      Order order = orderCreator.create(request);
      Long orderId = gateway.save(order);
      presenter.success(Response.of(orderId));
    } catch (InvalidOrder e) {
      presenter.failure(e.getMessage());
    }
  }

  @Builder
  public static class Request {
    public User customer;
    public String name;
    public String email;
    public String phone;
    public String address;
    @Builder.Default
    public List<Pizza> pizzas = new ArrayList<>();

    @Builder
    public static class Pizza {
      public Long baseId;
      @Builder.Default
      public List<Long> toppingIds = new ArrayList<>();
    }
  }

  @AllArgsConstructor(staticName = "of")
  public static class Response {
    public Long orderId;
  }
}
