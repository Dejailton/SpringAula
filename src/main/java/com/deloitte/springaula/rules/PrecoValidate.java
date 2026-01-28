package com.deloitte.springaula.rules;

import com.deloitte.springaula.dto.ProdutoRequestDTO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(20)
public class PrecoValidate implements ProdutoBusinessRule {

    @Override
    public Map<String, String> validate(Long id, ProdutoRequestDTO dto) {
        Map<String, String> errors = new HashMap<>();
        if (dto == null) return errors;

        Double preco = dto.preco();
        if (preco != null && preco < 0) {
            errors.put("preco", "preco deve ser maior ou igual a 0");
        }

        return errors;
    }
}
