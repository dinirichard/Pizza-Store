package com.nortal.pizzastore.usecase.registeruser;

import com.nortal.pizzastore.domain.user.Role;
import com.nortal.pizzastore.domain.user.User;
import com.nortal.pizzastore.domain.user.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserTest {

  @Mock
  private UserGateway gateway;
  @Mock
  private Encoder encoder;
  @Mock
  private RegisterUserPresenter presenter;

  private RegisterUser registerUser;

  @BeforeEach
  void setUp() {
    registerUser = new RegisterUser(gateway, encoder);
  }

  @Test
  void usernameAlreadyExists() {
    // given
    String username = "user";
    when(gateway.existsByUsername(username))
      .thenReturn(true);

    var request = RegisterUser.Request.builder()
      .username("user")
      .password("password")
      .roles(null)
      .build();

    // when
    registerUser.execute(request, presenter);

    // then
    verify(presenter).failure(String.format("Username '%s' already exists", username));
  }

  @Test
  void successfulRegistration() {
    // given
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    Set<Role> roles = new HashSet<>();
    roles.add(Role.ROLE_CUSTOMER);
    var request = RegisterUser.Request.builder()
      .username("user")
      .password("password")
      .roles(roles)
      .build();

    when(encoder.encode(anyString()))
      .thenReturn("encodedPassword");

    // when
    registerUser.execute(request, presenter);

    // then
    verify(gateway).save(userCaptor.capture());
    User user = userCaptor.getValue();
    assertThat(user.getUsername()).isEqualTo("user");
    assertThat(user.getPassword()).isEqualTo("encodedPassword");
    assertThat(user.getRoles()).containsExactly(Role.ROLE_CUSTOMER);
  }
}