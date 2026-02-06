package com.deloitte.springaula.service;

import com.deloitte.springaula.dto.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoReadService {
    List<ProdutoResponseDTO> listar();
    ProdutoResponseDTO buscarPorId(Long id);
}
