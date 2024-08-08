package com.rasmoo.api.rasfood.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardapioDto {

    private Integer id;

    private String nome;

    private String descricao;

    private BigDecimal valor;

    private String nomeCategoria;

    public CardapioDto(String nome, String descricao, BigDecimal valor, String nomeCategoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.nomeCategoria = nomeCategoria;
    }

    public CardapioDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}