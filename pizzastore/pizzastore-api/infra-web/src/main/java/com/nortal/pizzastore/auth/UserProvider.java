package com.nortal.pizzastore.auth;

import com.nortal.pizzastore.domain.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProvider implements UserDetailsService {

  private final UserGateway userGateway;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userGateway.findByUsername(username)
      .map(UserPrincipal::of)
      .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
