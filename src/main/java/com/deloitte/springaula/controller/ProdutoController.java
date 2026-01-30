package com.deloitte.springaula.controller;

import com.deloitte.springaula.service.ProdutoService;
import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.dto.ProdutoResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "API de Produtos rodando com Spring Boot";
    }

    @GetMapping
    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> obterProduto(@PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> adicionarProduto(@Valid @RequestBody ProdutoRequestDTO produtoDto) {
        ProdutoResponseDTO criado = produtoService.salvar(produtoDto);
        URI location = URI.create("/produtos/" + criado.id());
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produtoDto) {
        ProdutoResponseDTO atualizado = produtoService.atualizar(id, produtoDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        produtoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}