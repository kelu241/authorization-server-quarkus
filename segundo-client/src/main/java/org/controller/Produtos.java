package org.controller;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.oidc.IdToken;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Authenticated
@Path("home")
public class Produtos {

  @Inject
  SecurityIdentity securityIdentity;

  @Inject
  AccessTokenCredential accessTokenCredential;

  @Inject
  @IdToken
  JsonWebToken idToken;

  @Inject
  @RestClient
  ResourceServerClient2 resourceServerClient2;

  @GET
  public Response analise() {

    Map<String, Object> valores = new HashMap<>();

    valores.put("usuario", securityIdentity.getPrincipal().getName());
    valores.put("roles", securityIdentity.getRoles());

    // Access Token (token do client para chamadas a APIs)
    String rawAccessToken = accessTokenCredential.getToken();
    valores.put("access_token", rawAccessToken);

    // ID Token (token do usuário autenticado com claims OIDC)
    Map<String, Object> idTokenClaims = new HashMap<>();
    idTokenClaims.put("raw", idToken.getRawToken());
    idTokenClaims.put("subject", idToken.getSubject());
    idTokenClaims.put("name", idToken.getName());
    idTokenClaims.put("email", idToken.getClaim("email"));
    idTokenClaims.put("issued_at", idToken.getIssuedAtTime());
    idTokenClaims.put("expiration", idToken.getExpirationTime());
    idTokenClaims.put("issuer", idToken.getIssuer());
    valores.put("id_token", idTokenClaims);

    return Response.ok(valores).build();
  }

  @GET
  @Path("segundo-resource")
  public Response callResourceServer() {
    String token = accessTokenCredential.getToken();
    String body = resourceServerClient2.getProdutos("Bearer " + token);

    Map<String, Object> payload = new HashMap<>();
    payload.put("resource_body", body);

    return Response.ok(payload).build();
  }

}
