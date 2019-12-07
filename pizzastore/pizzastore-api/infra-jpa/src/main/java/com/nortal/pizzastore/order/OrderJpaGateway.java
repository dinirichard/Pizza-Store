package com.nortal.pizzastore.order;

import com.nortal.pizzastore.domain.order.Customer;
import com.nortal.pizzastore.domain.order.Order;
import com.nortal.pizzastore.domain.order.OrderGateway;
import com.nortal.pizzastore.domain.order.OrderItem;
import com.nortal.pizzastore.domain.product.Product;
import com.nortal.pizzastore.domain.user.User;
import com.nortal.pizzastore.product.ProductEntity;
import com.nortal.pizzastore.product.ProductRepository;
import com.nortal.pizzastore.user.UserEntity;
import com.nortal.pizzastore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Repository
@RequiredArgsConstructor
class OrderJpaGateway implements OrderGateway {

  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final OrderEntityMapper mapper;

  @Override
  public List<Order> findOrders() {
    // TODO! DONE After implementing logical deletion, find only active orders
    return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "created")).stream()
      .filter(t -> t.getOrder_state().equals("ACTIVE"))
      .map(mapper::toDomainEntity)
      .collect(toList());
  }

  @Override
  public List<Order> findOrdersByUser(User user) {
    // TODO! DONE After implementing logical deletion, find only active orders
    return orderRepository.findByCustomerUsernameOrderByCreatedDesc(user.getUsername()).stream()
            .filter(t -> t.getOrder_state().equals("ACTIVE"))
            .map(mapper::toDomainEntity)
            .collect(toList());
  }

  @Override
  public Optional<Order> findById(Long id) {
    return orderRepository.findById(id)
      .map(mapper::toDomainEntity);
  }

  @Override
  public Optional<Order> findByIdAndUser(Long id, User user) {
    return orderRepository.findById(id)
      .map(mapper::toDomainEntity);
  }

  @Override
  public Long save(Order order) {
    Customer customer = order.getCustomer();
    UserEntity userEntity = userRepository.findByUsername(customer.getUser().getUsername())
      .orElseThrow(() -> new RuntimeException("User not found"));

    OrderEntity entity = OrderEntity.builder()
      .created(LocalDateTime.now())
      .name(customer.getName())
      .email(customer.getEmail())
      .phone(customer.getPhone())
      .address(customer.getAddress())
      .order_state("ACTIVE")
      .customer(userEntity)
      .build();

    entity.setItems(createOrderItems(order, entity));
    entity = orderRepository.save(entity);
    return entity.getId();
  }

  @Override
  public void cancel(Order order) {
    // TODO! DONE Logical deletion
    Optional<OrderEntity> orderEntity = orderRepository.findById(order.getId());

    if(orderEntity.isPresent()){
      orderEntity.get().setOrder_state("CANCELLED");
      orderRepository.save(orderEntity.get());
    }
  }

  private Set<OrderItemEntity> createOrderItems(Order order, OrderEntity entity) {
    return order.getItems().stream()
      .map(item -> createOrderItem(item, entity))
      .collect(toSet());
  }

  private OrderItemEntity createOrderItem(OrderItem item, OrderEntity entity) {
    return OrderItemEntity.builder()
      .order(entity)
      .products(getProductEntities(item.getProducts()))
      .build();
  }

  private List<ProductEntity> getProductEntities(Set<Product> products) {
    return products.stream()
      .map(p -> productRepository.findById(p.getId()))
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(toList());
  }
}
