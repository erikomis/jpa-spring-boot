package com.rasmoo.api.rasfood.repository;


import com.rasmoo.api.rasfood.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {
    List<Endereco> findByCep(String cep);
}
