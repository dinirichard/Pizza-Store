package com.nortal.pizzastore.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class User {
  private String username;
  private String password;
  @Builder.Default
  private Set<Role> roles = new HashSet<>();

  public boolean isAdmin() {
    return roles.contains(Role.ROLE_ADMIN);
  }
}
