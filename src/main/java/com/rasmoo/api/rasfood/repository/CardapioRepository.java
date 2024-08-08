package com.rasmoo.api.rasfood.repository;

import com.rasmoo.api.rasfood.dto.CardapioDto;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.projection.CardapioProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardapioRepository extends PagingAndSortingRepository<Cardapio,Integer>,  JpaRepository<Cardapio,Integer> , JpaSpecificationExecutor<Cardapio> {

    @Query("SELECT new com.rasmoo.api.rasfood.dto.CardapioDto(c.nome, c.descricao, c.valor, c.categoria.nome) " +
            "FROM Cardapio c WHERE c.nome LIKE %:nome% AND c.disponivel = true")
    List<CardapioDto> findAllByNome(final String nome);


    @Query(value = "SELECT" +
            "    c.nome as nome," +
            "    c.descricao as descricao," +
            "    c.valor as valor," +
            "    cat.nome as nomeCategoria" +
            "    FROM cardapio c" +
            "    INNER JOIN categorias cat on c.categoria_id = cat.id" +
            "    WHERE c.categoria_id = :categoria AND c.disponivel = true",
            nativeQuery = true,
            countQuery = "SELECT count(*) FROM cardapio")
    Page<CardapioProjection> findAllByCategoria(final Integer categoria, final Pageable pageable);



    @Transactional
    @Modifying
    @Query("UPDATE Cardapio c SET c.disponivel = " +
            "CASE c.disponivel " +
            "WHEN true THEN false " +
            "ELSE true END " +
            "WHERE c.id = :id")
    Integer updateDisponibilidade(final Integer id);
}