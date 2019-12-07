package com.nortal.pizzastore.auth;

import com.nortal.pizzastore.domain.user.Role;
import com.nortal.pizzastore.usecase.registeruser.RegisterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.hibernate.validator.internal.util.CollectionHelper.asSet;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
class AuthController {

  private final AuthenticationManager authManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final RegisterUser registerUser;

  @PostMapping("/auth/login")
  ResponseEntity login(@RequestBody UserCredentials credentials) {
    return Optional.of(authManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.username, credentials.password)))
      .map(auth -> (UserPrincipal) auth.getPrincipal())
      .map(UserPrincipal::getUser)
      .map(user -> jwtTokenProvider.createToken(credentials.username, user.getRoles()))
      .map(token -> ok(UserToken.of(credentials.username, token)))
      .orElseThrow(() -> new BadCredentialsException("Authentication failed"));
  }

  @PostMapping("/auth/register")
  ResponseEntity register(@RequestBody UserCredentials credentials) {
    RegisterUserJsonPresenter presenter = new RegisterUserJsonPresenter();

    registerUser.execute(RegisterUser.Request.builder()
      .username(credentials.username)
      .password(credentials.password)
      .roles(asSet(Role.ROLE_CUSTOMER))
      .build(), presenter);

    if (presenter.isSuccessful()) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    return ResponseEntity.badRequest().body(presenter.reason);
  }
}
