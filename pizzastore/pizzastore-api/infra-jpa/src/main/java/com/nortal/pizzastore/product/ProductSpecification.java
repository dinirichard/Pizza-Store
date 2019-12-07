package com.nortal.pizzastore.product;

import org.springframework.data.jpa.domain.Specification;

class ProductSpecification {

  static Specification<ProductEntity> productsByCategory(String categoryCode) {
    if (categoryCode == null) {
      return null;
    }
    return (root, query, cb) ->
      cb.equal(root.get("category").get("code"), categoryCode);
  }
}
