package org.luciano.controller;

import io.smallrye.mutiny.Multi;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("Produtos")
@Authenticated
public class Produtos {
  @GET
  public Uni<String> testaResource() {

    String completar = "Esse recurso está sendo acessado pelo client";
    return Uni.createFrom().item(String.format("Recuso acessado:%s", completar));
  }

  @GET
  @Path("segundo")
  public Multi<String> testaResourceSegundo() {

    String completar = "Esse recurso é o segundo get";
    String completar2 = "Esse recurso é o segundo get2";
    return Multi.createFrom().items(String.format("Recuso acessado:%s", completar),
        String.format("Recuso acessado:%s", completar2));
  }

}
