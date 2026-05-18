package org.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/Produtos/segundo")
@RegisterRestClient(configKey = "resource-server")
public interface ResourceServerClient2 {

  @GET
  @Path("segundo")
  String getProdutos(@HeaderParam("Authorization") String authorization);
}
