package com.nortal.pizzastore.usecase.registeruser;

import com.nortal.pizzastore.domain.user.Role;
import com.nortal.pizzastore.domain.user.User;
import com.nortal.pizzastore.domain.user.UserGateway;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RegisterUser {

  private final UserGateway gateway;
  private final Encoder encoder;

  public void execute(Request request, RegisterUserPresenter presenter) {

    if (gateway.existsByUsername(request.username)) {
      presenter.failure(String.format("Username '%s' already exists", request.username));
      return;
    }
    User user = User.builder()
      .username(request.username)
      .password(encoder.encode(request.password))
      .roles(request.roles)
      .build();
    gateway.save(user);
  }

  @Builder
  public static class Request {
    public String username;
    public String password;
    public Set<Role> roles;
  }
}
