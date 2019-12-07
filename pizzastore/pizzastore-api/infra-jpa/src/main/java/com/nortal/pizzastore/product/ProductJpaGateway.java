package com.nortal.pizzastore.product;

import com.nortal.pizzastore.domain.product.Product;
import com.nortal.pizzastore.domain.product.ProductGateway;
import com.nortal.pizzastore.usecase.findproducts.FindProducts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.nortal.pizzastore.product.ProductSpecification.productsByCategory;
import static java.util.stream.Collectors.toList;

@Repository
@AllArgsConstructor
class ProductJpaGateway implements ProductGateway {

  private ProductRepository repository;

  @Override
  public List<Product> findProducts(FindProducts.Request request) {
    return repository.findAll(productsByCategory(request.categoryCode)).stream()
      .map(ProductEntityMapper::toDomainEntity)
      .collect(toList());
  }

  @Override
  public Optional<Product> findProduct(Long id) {
    return repository.findById(id)
      .map(ProductEntityMapper::toDomainEntity);
  }
}
