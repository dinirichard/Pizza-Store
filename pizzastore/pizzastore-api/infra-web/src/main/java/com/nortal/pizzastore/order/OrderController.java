package com.nortal.pizzastore.order;

import com.nortal.pizzastore.auth.UserPrincipal;
import com.nortal.pizzastore.usecase.cancelorder.CancelOrder;
import com.nortal.pizzastore.usecase.findorders.FindOrders;
import com.nortal.pizzastore.usecase.getorder.GetOrder;
import com.nortal.pizzastore.usecase.placeorder.PlaceOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.nortal.pizzastore.order.OrderResourceMapper.toRequest;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final PlaceOrder placeOrder;
  private final FindOrders findOrders;
  private final GetOrder getOrder;
  private final CancelOrder cancelOrder;

  @PostMapping
  ResponseEntity placeOrder(@RequestBody OrderInputResource resource,
                            @AuthenticationPrincipal UserPrincipal principal) {

    PlaceOrderResultPresenter presenter = new PlaceOrderResultPresenter();
    placeOrder.execute(toRequest(resource, principal.getUser()), presenter);

    if (presenter.isSuccessful()) {
      return ResponseEntity
        .created(getLocationUri(presenter.orderId))
        .build();
    }
    return ResponseEntity.badRequest()
      .body(presenter.failureReason);
  }

  private URI getLocationUri(Long orderId) {
    return ServletUriComponentsBuilder
      .fromCurrentRequest().path("/{id}")
      .buildAndExpand(orderId)
      .toUri();
  }

  @GetMapping
  List<OrderOutputResource> getOrders(@AuthenticationPrincipal UserPrincipal principal) {

    FindOrdersJsonPresenter presenter = new FindOrdersJsonPresenter();
    findOrders.execute(FindOrders.Request.of(principal.getUser()), presenter);

    return presenter.result;
  }

  @GetMapping("{id}")
  ResponseEntity<OrderOutputResource> getOrder(@PathVariable Long id,
                                               @AuthenticationPrincipal UserPrincipal principal) {

    GetOrderJsonPresenter presenter = new GetOrderJsonPresenter();
    getOrder.execute(GetOrder.Request.of(id, principal.getUser()), presenter);

    return ResponseEntity.of(Optional.ofNullable(presenter.result));
  }

  @DeleteMapping("{id}")
  ResponseEntity cancelOrder(@PathVariable Long id,
                             @AuthenticationPrincipal UserPrincipal principal) {

    CancelOrderJsonPresenter presenter = new CancelOrderJsonPresenter();
    cancelOrder.execute(CancelOrder.Request.of(id, principal.getUser()), presenter);

    if (presenter.isSuccessful()) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().body(presenter.failureReason);
  }
}
