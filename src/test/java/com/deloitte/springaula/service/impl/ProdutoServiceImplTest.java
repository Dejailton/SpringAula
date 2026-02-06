package com.deloitte.springaula.service.impl;

import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.dto.ProdutoResponseDTO;
import com.deloitte.springaula.exception.BusinessRuleViolationException;
import com.deloitte.springaula.exception.ProdutoNotFoundException;
import com.deloitte.springaula.mapper.ProdutoMapper;
import com.deloitte.springaula.model.Produto;
import com.deloitte.springaula.repository.ProdutoRepository;
import com.deloitte.springaula.service.rules.ProdutoBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @Mock
    private ProdutoBusinessRule rule;

    private ProdutoServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProdutoServiceImpl(produtoRepository, produtoMapper, List.of(rule));
    }

    @Test
        //Teste de salvar: deve persistir e retornar o DTO de resposta")
    void salvar_devePersistirERetornarResposta() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Caneta", 2.5);
        Produto entity = new Produto(null, "Caneta", 2.5);
        Produto saved = new Produto(1L, "Caneta", 2.5);
        ProdutoResponseDTO response = new ProdutoResponseDTO(1L, "Caneta", 2.5);

        when(rule.validate(null, dto)).thenReturn(Collections.emptyMap());
        when(produtoMapper.toEntity(dto)).thenReturn(entity);
        when(produtoRepository.save(entity)).thenReturn(saved);
        when(produtoMapper.toResponseDto(saved)).thenReturn(response);

        ProdutoResponseDTO resultado = service.salvar(dto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(1L);
        assertThat(resultado.nome()).isEqualTo("Caneta");
    }

    @Test
        //Teste para salvar: quando há violação de regra de negócio deve lançar BusinessRuleViolationException"
    void salvar_quandoViolacaoRegraNegocio() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Caneta", 2.5);
        when(rule.validate(null, dto)).thenReturn(Map.of("nome", "já existe"));

        assertThrows(BusinessRuleViolationException.class, () -> service.salvar(dto));
    }

    @Test
        // Teste para buscar por ID: quando não encontrado deve lançar ProdutoNotFoundException"
    void buscarPorId_quandoNaoEncontrado() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ProdutoNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
        //Teste da função atualizar: deve atualizar a entidade existente e retornar o DTO atualizado")
    void atualizar_deveAtualizarERetornarResposta() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Lapis", 1.0);
        Produto existing = new Produto(2L, "LapisAntigo", 0.5);
        Produto updated = new Produto(2L, "Lapis", 1.0);
        ProdutoResponseDTO response = new ProdutoResponseDTO(2L, "Lapis", 1.0);

        when(produtoRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(rule.validate(2L, dto)).thenReturn(Collections.emptyMap());
        doNothing().when(produtoMapper).updateEntityFromDto(dto, existing);
        when(produtoRepository.save(existing)).thenReturn(updated);
        when(produtoMapper.toResponseDto(updated)).thenReturn(response);

        ProdutoResponseDTO result = service.atualizar(2L, dto);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(2L);
        assertThat(result.nome()).isEqualTo("Lapis");
    }

    @Test
    void remover_quandoExiste_deveDeletar() {
        Long id = 10L;
        when(produtoRepository.existsById(id)).thenReturn(true);

        service.remover(id);

        verify(produtoRepository, times(1)).deleteById(id);
    }

    @Test
    void remover_quandoNaoExiste_deveLancarProdutoNotFound() {
        Long id = 11L;
        when(produtoRepository.existsById(id)).thenReturn(false);

        assertThrows(ProdutoNotFoundException.class, () -> service.remover(id));
    }
}