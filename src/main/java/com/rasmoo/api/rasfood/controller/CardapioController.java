package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.dto.CardapioDto;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.CardapioRepository;
import com.rasmoo.api.rasfood.repository.projection.CardapioProjection;
import com.rasmoo.api.rasfood.repository.specification.CardapioSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping(value = "/cardapio")
public class CardapioController {



    private CardapioRepository cardapioRepository;

    private ObjectMapper objectMapper;

    public CardapioController(CardapioRepository cardapioRepository, ObjectMapper objectMapper) {
        this.cardapioRepository = cardapioRepository;
        this.objectMapper = objectMapper;
    }


    @GetMapping
    public ResponseEntity<Page<Cardapio>> consultarTodos(@RequestParam("page")Integer page, @RequestParam("size")Integer size,
                                                         @RequestParam(value = "sort",required = false)Sort.Direction sort,
                                                         @RequestParam(value = "property", required = false)String property) {
        System.out.println("page " + page +"/// size " + size + "/// sort " + sort + "/// property " + property);
        Pageable pageable = Objects.nonNull(sort)
                ? PageRequest.of(page,size, Sort.by(sort,property))
                : PageRequest.of(page,size);

        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll(pageable));
    }

    @GetMapping("/nome/{nome}/disponivel")
    public ResponseEntity<List<Cardapio>> consultarTodos(@PathVariable("nome") final String nome,
                                                         @RequestParam("page")Integer page, @RequestParam("size")Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll(Specification
                        .where(CardapioSpec.nome(nome))
                        .and(CardapioSpec.disponivel(true))
                ,pageable).getContent());
    }

    @GetMapping("/categoria/{categoriaId}/disponivel")
    public ResponseEntity<List<Cardapio>> consultarTodos(@PathVariable("categoriaId") final Integer categoriaId,
                                                         @RequestParam("page")Integer page, @RequestParam("size")Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll(Specification
                .where(CardapioSpec.categoria(categoriaId))
                .and(CardapioSpec.disponivel(true)),pageable).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cardapio> consultarPorId(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapioEncontrado = cardapioRepository.findById(id);
        if (cardapioEncontrado.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cardapioEncontrado.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPorId(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapioEncontrado = cardapioRepository.findById(id);
        if (cardapioEncontrado.isPresent()) {
            cardapioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Elemento não encontrado");
    }

    @PostMapping
    public ResponseEntity<Cardapio> criar(@RequestBody final Cardapio cardapio) throws JsonMappingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cardapioRepository.save(cardapio));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cardapio> atualizar(@PathVariable("id") final Integer id, @RequestBody final Cardapio cardapio) throws JsonMappingException {
        Optional<Cardapio> cardapioEncontrado = this.cardapioRepository.findById(id);
        if (cardapioEncontrado.isPresent()) {
            objectMapper.updateValue(cardapioEncontrado.get(), cardapio);
            return ResponseEntity.status(HttpStatus.OK).body(this.cardapioRepository.save(cardapioEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
