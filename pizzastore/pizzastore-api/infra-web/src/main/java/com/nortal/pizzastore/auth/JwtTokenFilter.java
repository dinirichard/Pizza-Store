package com.nortal.pizzastore.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
    throws IOException, ServletException {

    jwtTokenProvider.resolveToken((HttpServletRequest) req)
      .filter(jwtTokenProvider::validateToken)
      .map(jwtTokenProvider::getAuthentication)
      .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

    filterChain.doFilter(req, res);
  }
}
