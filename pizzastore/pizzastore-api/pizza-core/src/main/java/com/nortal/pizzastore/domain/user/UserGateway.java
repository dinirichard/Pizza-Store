package com.nortal.pizzastore.domain.user;

import java.util.Optional;

public interface UserGateway {

  Optional<User> findByUsername(String username);

  void save(User user);

  boolean existsByUsername(String username);
}
