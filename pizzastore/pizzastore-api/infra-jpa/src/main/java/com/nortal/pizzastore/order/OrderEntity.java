package com.nortal.pizzastore.order;

import com.nortal.pizzastore.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created")
  private LocalDateTime created;

  @Column(name = "customer_name")
  private String name;

  @Column(name = "customer_email")
  private String email;

  @Column(name = "customer_phone")
  private String phone;

  @Column(name = "customer_address")
  private String address;

  @Column(name = "order_state")
  private String order_state;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private UserEntity customer;

  @Builder.Default
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  private Set<OrderItemEntity> items = new HashSet<>();
}
