package com.luciano.authserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserStoreConfig {

  @Bean
  public UserDetailsService UserDetailService() {

    var userDetailManager = new InMemoryUserDetailsManager();

    userDetailManager.createUser(User.withUsername("user")
        .password("{noop}password")
        .roles("USER")
        .build());

    return userDetailManager;

  }

}
