package com.nortal.pizzastore.auth;

import com.nortal.pizzastore.usecase.registeruser.Encoder;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
class PasswordEncoderImpl implements Encoder, PasswordEncoder {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private PasswordEncoder passwordEncoder;

  PasswordEncoderImpl(){
    passwordEncoder = passwordEncoder();
  }


  @Override
  public String encode(CharSequence rawPassword) {

    // TODO! Proper password encoding... DONE
      String encodedPassword = passwordEncoder.encode(rawPassword);

    return encodedPassword;
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {

    // TODO! Proper password encoding... DONE

    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
