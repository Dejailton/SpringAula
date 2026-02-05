package com.deloitte.springaula.service;

import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.dto.ProdutoResponseDTO;

public interface ProdutoWriteService {
    ProdutoResponseDTO salvar(ProdutoRequestDTO produto);
    ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produto);
    void remover(Long id);
}
