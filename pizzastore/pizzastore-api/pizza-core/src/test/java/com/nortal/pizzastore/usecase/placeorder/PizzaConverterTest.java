package com.nortal.pizzastore.usecase.placeorder;

import com.nortal.pizzastore.domain.product.Product;
import com.nortal.pizzastore.domain.product.ProductCategory;
import com.nortal.pizzastore.domain.product.ProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PizzaConverterTest {

  @Mock
  private ProductGateway productGateway;

  private PizzaConverter pizzaConverter;
  private PlaceOrder.Request.Pizza pizza;

  @BeforeEach
  void setUp() {
    pizzaConverter = new PizzaConverter(productGateway);

    pizza = PlaceOrder.Request.Pizza.builder()
      .baseId(1L)
      .toppingIds(List.of(2L))
      .build();
  }

  @Test
  void validPizzaProducts() {
    // given
    Product base = Product.builder().category(ProductCategory.of("PIZZA_BASE")).build();
    given(productGateway.findProduct(1L))
      .willReturn(Optional.of(base));

    Product topping = Product.builder().category(ProductCategory.of("PIZZA_TOPPING")).build();
    given(productGateway.findProduct(2L))
      .willReturn(Optional.of(topping));

    // when
    Set<Product> products = pizzaConverter.toProducts(pizza);

    // then
    assertThat(products).contains(base, topping);
  }

  @Test
  void invalidBase() {
    given(productGateway.findProduct(1L))
      .willReturn(Optional.of(Product.builder().category(ProductCategory.of("NOT_A_BASE")).build()));

    // when/then
    assertThrows(PizzaConverter.InvalidBase.class, () -> pizzaConverter.toProducts(pizza));
  }

  @Test
  void invalidTopping() {
    given(productGateway.findProduct(1L))
      .willReturn(Optional.of(Product.builder().category(ProductCategory.of("PIZZA_BASE")).build()));

    given(productGateway.findProduct(2L))
      .willReturn(Optional.of(Product.builder().category(ProductCategory.of("NOT_A_TOPPING")).build()));

    // when/then
    assertThrows(PizzaConverter.InvalidTopping.class, () -> pizzaConverter.toProducts(pizza));
  }
}