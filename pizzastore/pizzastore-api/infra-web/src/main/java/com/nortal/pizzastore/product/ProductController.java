package com.nortal.pizzastore.product;

import com.nortal.pizzastore.usecase.findproducts.FindProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
class ProductController {

  private final FindProducts findProducts;

  @GetMapping
  List<ProductResource> findProducts(ProductSearchParams params) { //receives the param e.g 'PIZZA_TOPPING' or 'PIZZA_BASE'
    FindProductsJsonPresenter presenter = new FindProductsJsonPresenter();  // Interpretes the json
    findProducts.execute(FindProducts.Request.of(params.categoryCode), presenter);// then searches for the product using the categoryCode

    return presenter.result;
  }
}
