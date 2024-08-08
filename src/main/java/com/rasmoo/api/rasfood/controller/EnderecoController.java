package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.rasmoo.api.rasfood.entity.Endereco;
import com.rasmoo.api.rasfood.repository.EnderecoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping(value = "/endereco")
@RestController
public class EnderecoController {


    private EnderecoRepository enderecoRepository;

    private ObjectMapper objectMapper;


    public EnderecoController(EnderecoRepository enderecoRepository, ObjectMapper objectMapper) {
        this.enderecoRepository = enderecoRepository;
        this.objectMapper = objectMapper;
    }





    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<Endereco>> consultarTodosPorCep(@PathVariable("cep") final String cep){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findByCep(cep));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> consultarPorEmailCpf(@PathVariable("id") final Integer id){
        return enderecoRepository.findById(id)
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable("id") final Integer id, @RequestBody final Endereco endereco) throws JsonMappingException {
        Optional<Endereco> enderecoEncontrado = this.enderecoRepository.findById(id);
        if (enderecoEncontrado.isPresent()){
            objectMapper.updateValue(enderecoEncontrado.get(),endereco);
            return ResponseEntity.status(HttpStatus.OK).body(this.enderecoRepository.save(enderecoEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
