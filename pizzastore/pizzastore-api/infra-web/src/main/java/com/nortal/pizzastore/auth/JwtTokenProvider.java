package com.nortal.pizzastore.auth;

import com.nortal.pizzastore.domain.user.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  @Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 3600000; // 1h
  private final UserDetailsService userDetailsService;
  private SecretKey secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  String createToken(String username, Collection<Role> roles) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", roles.stream()
      .map(Enum::name)
      .collect(toList()));

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(validity)
      .signWith(secretKey, SignatureAlgorithm.HS256)
      .compact();
  }

  Authentication getAuthentication(String token) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  Optional<String> resolveToken(HttpServletRequest req) {
    return Optional.ofNullable(req.getHeader("Authorization"))
      .filter(header -> header.startsWith("Bearer "))
      .map(header -> header.substring(7));
  }

  boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
