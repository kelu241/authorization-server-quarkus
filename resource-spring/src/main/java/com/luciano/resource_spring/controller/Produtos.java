package com.luciano.resource_spring.controller;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController("tarefas")
public class Produtos {

  @RequestMapping("produtos")
  public String getProdutos(@AuthenticationPrincipal Jwt jwt) {

    return

    """
         <h1>Lista de Produtos %s</h1>
         <ol>
        <li>Ps5</li>
         <li>Ps4</li>
         <li>Ps3</li>
         <li>Ps1</li>
         <li>xbox one</li>
         <li>Ps vr 2</li>
         </ol>

         """.formatted(jwt.getSubject());

  }

}
