package com.deloitte.springaula.controller;

import com.deloitte.springaula.service.ProdutoService;
import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.dto.ProdutoResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar produto", description = "Cadastra um novo produto com os dados fornecidos.")
    public ResponseEntity<ProdutoResponseDTO> adicionarProduto(@Valid @RequestBody ProdutoRequestDTO produtoDto) {
        ProdutoResponseDTO criado = produtoService.salvar(produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Retorna a lista de todos os produtos cadastrados.")
    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID.")
    public ResponseEntity<ProdutoResponseDTO> obterProduto(@PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente pelo ID.")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produtoDto) {
        ProdutoResponseDTO atualizado = produtoService.atualizar(id, produtoDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto", description = "Remove um produto pelo ID.")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        produtoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}