package com.nortal.pizzastore.usecase.findproducts;

import com.nortal.pizzastore.domain.product.Product;
import com.nortal.pizzastore.domain.product.ProductGateway;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindProducts {

  private final ProductGateway gateway;

  public void execute(Request request, FindProductsPresenter presenter) {
    List<Product> products = gateway.findProducts(request);
    presenter.present(Response.of(products));
  }

  @AllArgsConstructor(staticName = "of")
  public static class Request {
    public String categoryCode;
  }

  @AllArgsConstructor(staticName = "of")
  public static class Response {
    public List<Product> products;
  }
}
