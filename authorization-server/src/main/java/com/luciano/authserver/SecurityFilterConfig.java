package com.luciano.authserver;

import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityFilterConfig {
  @Order(1)
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

    http.oauth2ResourceServer(new Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>>() {
      @Override
      public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> conf) {

        conf.jwt(Customizer.withDefaults());
      }
    });

    http.exceptionHandling(new Customizer<ExceptionHandlingConfigurer<HttpSecurity>>() {
      @Override
      public void customize(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {

        exceptionHandling.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

      }
    });
    return http.build();

  }

  @Order(2)
  @Bean
  SecurityFilterChain defaultSecureFilterChain(HttpSecurity http) throws Exception {

    http.cors(Customizer.withDefaults());

    http.authorizeHttpRequests(
        new Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {

          @Override
          public void customize(
              AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {

            authorize.requestMatchers("/error", "/login", "/assets", "/webjars/**", "/favicon.ico",
                "/.well-know/appspecific/**").permitAll().anyRequest().authenticated();

          }
        });

    http.formLogin(Customizer.withDefaults());

    return http.build();
  }

  // tudo ok na terra
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://172.29.237.100:8080", "http://172.29.237.100:8081")); // origem do Quarkus
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

}
