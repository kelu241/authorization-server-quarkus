package com.luciano.client_spring.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class Produtos {
  public WebClient webClient;

  public Produtos(WebClient.Builder builder) {

    this.webClient = builder.baseUrl("http://172.29.237.100:9090").build();

  }

  @GetMapping("home")
  public Mono<String> home(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oauth2AuthorizedClient,
      @AuthenticationPrincipal OidcUser oidcUser) {

    return Mono.just(
        """
            <h1>Acces token : %s </h1>
            <h1>RefreshToken : %s </h1>
            <h1> Id token %s </h1>
            <h1> Claims %s </h1>
            """
            .formatted(oauth2AuthorizedClient.getAccessToken().getTokenValue(),
                oauth2AuthorizedClient.getRefreshToken().getTokenValue(),
                oidcUser.getIdToken().getTokenValue(),
                oidcUser.getClaims()));

  }

  @GetMapping("resource-spring")
  public Mono<String> reource(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
    return this.webClient.get()
        .uri("Produtos")
        .header("Authorization", "Bearer %s".formatted(client.getAccessToken().getTokenValue()))
        .retrieve()
        .bodyToMono(String.class);
  }

}
