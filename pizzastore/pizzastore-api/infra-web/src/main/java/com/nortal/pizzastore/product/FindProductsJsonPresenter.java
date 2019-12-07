package com.nortal.pizzastore.product;

import com.nortal.pizzastore.usecase.findproducts.FindProducts;
import com.nortal.pizzastore.usecase.findproducts.FindProductsPresenter;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FindProductsJsonPresenter implements FindProductsPresenter {

  List<ProductResource> result;

  @Override
  public void present(FindProducts.Response response) {
    result = response.products.stream()
      .map(ProductResource::new)
      .collect(toList());
  }
}
