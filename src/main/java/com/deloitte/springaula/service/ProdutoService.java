package com.deloitte.springaula.service;

import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.dto.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoService {
    ProdutoResponseDTO salvar(ProdutoRequestDTO produto);
    List<ProdutoResponseDTO> listar();
    ProdutoResponseDTO buscarPorId(Long id);
    ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produto);
    void remover(Long id);
}
