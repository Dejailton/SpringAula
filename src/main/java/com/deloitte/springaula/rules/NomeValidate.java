package com.deloitte.springaula.rules;

import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(10)
public class NomeValidate implements ProdutoBusinessRule {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Map<String, String> validate(Long id, ProdutoRequestDTO dto) {
        Map<String, String> errors = new HashMap<>();
        if (dto == null) return errors;

        if (dto.nome() == null || dto.nome().isBlank()) {
            errors.put("nome", "nome é obrigatório");
            return errors;
        }

        produtoRepository.findByNome(dto.nome()).ifPresent(existing -> {
            if (id == null || !existing.getId().equals(id)) {
                errors.put("nome", "já existe outro produto com esse nome");
            }
        });

        return errors;
    }
}
