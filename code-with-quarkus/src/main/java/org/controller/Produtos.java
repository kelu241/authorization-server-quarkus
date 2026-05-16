package org.controller;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.oidc.IdToken;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

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

  @GET
  public Response alise() {

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

}
