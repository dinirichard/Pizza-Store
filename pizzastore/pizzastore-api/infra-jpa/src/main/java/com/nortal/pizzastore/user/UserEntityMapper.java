package com.nortal.pizzastore.user;

import com.nortal.pizzastore.domain.user.Role;
import com.nortal.pizzastore.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class UserEntityMapper {

  private final RoleRepository roleRepository;

  public User toDomainEntity(UserEntity jpaEntity) {
    return User.builder()
      .username(jpaEntity.getUsername())
      .password(jpaEntity.getEncryptedPassword())
      .roles(getRoles(jpaEntity))
      .build();
  }

  private Set<Role> getRoles(UserEntity jpaEntity) {
    return jpaEntity.getRoles().stream()
      .map(r -> Role.valueOf(r.getCode()))
      .collect(Collectors.toSet());
  }

  UserEntity toJpaEntity(User user) {
    return UserEntity.builder()
      .username(user.getUsername())
      .encryptedPassword(user.getPassword())
      .roles(getRoles(user.getRoles()))
      .build();
  }

  private Set<RoleEntity> getRoles(Set<Role> roles) {
    return roles.stream()
      .map(role -> roleRepository.getOne(role.name()))
      .collect(toSet());
  }
}
