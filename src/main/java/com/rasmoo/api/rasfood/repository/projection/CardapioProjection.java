package com.rasmoo.api.rasfood.repository.projection;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CardapioProjection {
    Integer getId();

    String getNome();

    String getDescricao();

    BigDecimal getValor();

    String getNomeCategoria();

}