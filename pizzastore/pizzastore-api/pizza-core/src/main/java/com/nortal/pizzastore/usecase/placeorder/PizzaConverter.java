package com.nortal.pizzastore.usecase.placeorder;

import com.nortal.pizzastore.domain.order.InvalidOrder;
import com.nortal.pizzastore.domain.product.Product;
import com.nortal.pizzastore.domain.product.ProductGateway;
import com.nortal.pizzastore.usecase.placeorder.PlaceOrder.Request.Pizza;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
class PizzaConverter {

  private static final String BASE = "PIZZA_BASE";
  private static final String TOPPING = "PIZZA_TOPPING";

  private final ProductGateway productGateway;

  Set<Product> toProducts(Pizza pizza) {
    Set<Product> products = new HashSet<>();
    products.add(getBase(pizza));
    products.addAll(getToppings(pizza));

    return products;
  }

  private List<Product> getToppings(Pizza pizza) {
    return pizza.toppingIds.stream()
      .map(this::getTopping)
      .collect(toList());
  }

  private Product getBase(Pizza pizza) {
    return productGateway.findProduct(pizza.baseId)
      .filter(category(BASE))
      .orElseThrow(InvalidBase::new);
  }

  private Product getTopping(Long toppingId) {
    return productGateway.findProduct(toppingId)
      .filter(category(TOPPING))
      .orElseThrow(InvalidTopping::new);
  }

  private Predicate<Product> category(String categoryCode) {
    return p -> categoryCode.equals(p.getCategoryCode());
  }

  class InvalidTopping extends InvalidOrder {
    InvalidTopping() {
      super("invalid pizza topping");
    }
  }

  class InvalidBase extends InvalidOrder {
    InvalidBase() {
      super("invalid pizza base");
    }
  }
}
