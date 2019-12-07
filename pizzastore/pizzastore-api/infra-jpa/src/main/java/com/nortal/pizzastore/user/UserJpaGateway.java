package com.nortal.pizzastore.user;

import com.nortal.pizzastore.domain.user.User;
import com.nortal.pizzastore.domain.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaGateway implements UserGateway {

  private final UserRepository userRepository;
  private final UserEntityMapper mapper;

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username)
      .map(mapper::toDomainEntity);
  }

  @Override
  public void save(User user) {
    UserEntity entity = mapper.toJpaEntity(user);
    userRepository.save(entity);
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}
