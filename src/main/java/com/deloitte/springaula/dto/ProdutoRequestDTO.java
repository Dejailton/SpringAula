package com.deloitte.springaula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequestDTO(
        @NotBlank(message = "nome é obrigatório") String nome,
        @NotNull(message = "preco é obrigatório") @Positive(message = "preco deve ser maior que zero") Double preco
) {
}
