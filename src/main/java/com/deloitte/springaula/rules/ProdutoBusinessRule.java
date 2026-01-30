package com.deloitte.springaula.rules;

import com.deloitte.springaula.dto.ProdutoRequestDTO;

import java.util.Map;

public interface ProdutoBusinessRule {
    Map<String, String> validate(Long id, ProdutoRequestDTO dto);
}
