package com.nortal.pizzastore.order;

import com.nortal.pizzastore.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
class OrderItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  @Builder.Default
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "order_item_product", joinColumns = {
    @JoinColumn(name = "order_item_id", nullable = false)},
    inverseJoinColumns = {@JoinColumn(name = "product_id", nullable = false)})
  private List<ProductEntity> products = new ArrayList<>();
}
