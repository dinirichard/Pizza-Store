package com.nortal.pizzastore.usecase.placeorder;

import com.nortal.pizzastore.domain.order.Customer;
import com.nortal.pizzastore.domain.order.InvalidOrder;
import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderItem;
import com.nortal.pizzastore.domain.product.Product;
import com.nortal.pizzastore.domain.product.ProductCategory;
import com.nortal.pizzastore.domain.user.Role;
import com.nortal.pizzastore.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ReflectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderCreatorTest {

  @Mock
  private PizzaConverter pizzaConverter;

  private OrderCreator orderCreator;

  @BeforeEach
  void setUp() {
    orderCreator = new OrderCreator(pizzaConverter);
  }

  @Test
  void validOrder() {
    // given
    PlaceOrder.Request.Pizza pizza = PlaceOrder.Request.Pizza.builder()
      .baseId(1L)
      .toppingIds(List.of(2L))
      .build();

    PlaceOrder.Request request = PlaceOrder.Request.builder()
      .name("name")
      .email("email@email.com")
      .phone("phone")
      .address("address")
      .customer(User.builder().roles(new HashSet<>(List.of(Role.ROLE_CUSTOMER))).build())
      .pizzas(List.of(pizza))
      .build();

    Product base = Product.builder()
      .name("base")
      .category(ProductCategory.of("PIZZA_BASE"))
      .price(BigDecimal.valueOf(5.00))
      .build();

    Product topping = Product.builder()
      .name("topping")
      .category(ProductCategory.of("PIZZA_TOPPING"))
      .price(BigDecimal.valueOf(1.00))
      .build();

    given(pizzaConverter.toProducts(pizza))
      .willReturn(new HashSet<>(List.of(base, topping)));

    // when
    Order order = orderCreator.create(request);

    // then
    Customer customer = order.getCustomer();
    assertThat(customer.getName()).isEqualTo("name");
    assertThat(customer.getEmail()).isEqualTo("email@email.com");
    assertThat(customer.getPhone()).isEqualTo("phone");
    assertThat(customer.getAddress()).isEqualTo("address");

    assertThat(order.getCreated()).isBefore(LocalDateTime.now());
    assertThat(order.getItems()).containsAll(List.of(OrderItem.of(new HashSet<>(List.of(base, topping)))));
  }

  @Nested
  class InvalidCustomer {

    PlaceOrder.Request request;

    @BeforeEach
    void setUp() {
      request = PlaceOrder.Request.builder()
        .name("name")
        .email("email@email.com")
        .phone("phone")
        .address("address")
        .customer(User.builder().roles(new HashSet<>(List.of(Role.ROLE_CUSTOMER))).build())
        .pizzas(List.of(PlaceOrder.Request.Pizza.builder()
          .baseId(1L)
          .toppingIds(List.of(2L))
          .build()))
        .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "email", "phone", "address"})
    void fieldIsNull(String fieldName) throws NoSuchFieldException {
      // given
      ReflectionUtils.setField(PlaceOrder.Request.class.getField(fieldName), request, null);

      // when/then
      assertThrows(InvalidOrder.class, () -> orderCreator.create(request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "email", "phone", "address"})
    void fieldIsEmpty(String fieldName) throws NoSuchFieldException {
      // given
      ReflectionUtils.setField(PlaceOrder.Request.class.getField(fieldName), request, "");

      // when/then
      assertThrows(InvalidOrder.class, () -> orderCreator.create(request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "email", "phone", "address"})
    void fieldIsBlank(String fieldName) throws NoSuchFieldException {
      // given
      ReflectionUtils.setField(PlaceOrder.Request.class.getField(fieldName), request, " ");

      // when/then
      assertThrows(InvalidOrder.class, () -> orderCreator.create(request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mail", "bla@mail", "bla@", "@mail.com"})
    void email(String invalidValue) {
      // given
      request.email = invalidValue;

      // when/then
      assertThrows(InvalidOrder.class, () -> orderCreator.create(request));
    }
  }
}
