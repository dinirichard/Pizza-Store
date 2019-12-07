package com.nortal.pizzastore.usecase.findproducts;

import com.nortal.pizzastore.domain.product.Product;
import com.nortal.pizzastore.domain.product.ProductCategory;
import com.nortal.pizzastore.domain.product.ProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindProductsTest {

  @Mock
  private ProductGateway gateway;
  @Mock
  private FindProductsPresenter presenter;

  private FindProducts findProducts;

  @BeforeEach
  void setUp() {
    findProducts = new FindProducts(gateway);
  }

  @Test
  void execute() {
    // given
    var request = FindProducts.Request.of("PIZZA_TOPPING");

    Product bacon = Product.builder()
      .name("Bacon")
      .category(ProductCategory.of("PIZZA_TOPPING"))
      .price(BigDecimal.valueOf(1.10))
      .build();

    when(gateway.findProducts(request))
      .thenReturn(List.of(bacon));

    ArgumentCaptor<FindProducts.Response> responseCaptor = ArgumentCaptor.forClass(FindProducts.Response.class);

    // when
    findProducts.execute(request, presenter);

    // then
    verify(presenter).present(responseCaptor.capture());
    FindProducts.Response response = responseCaptor.getValue();
    assertThat(response.products).containsExactly(bacon);
  }
}