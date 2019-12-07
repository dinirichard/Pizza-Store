package com.nortal.pizzastore.product;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "product_category")
public class ProductCategoryEntity {

  @Id
  @Column(name = "code", nullable = false)
  private String code;
}
