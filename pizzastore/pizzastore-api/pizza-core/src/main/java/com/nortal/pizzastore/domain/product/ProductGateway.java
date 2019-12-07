package com.nortal.pizzastore.domain.product;

import com.nortal.pizzastore.usecase.findproducts.FindProducts;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {

  List<Product> findProducts(FindProducts.Request request);

  Optional<Product> findProduct(Long id);
}
