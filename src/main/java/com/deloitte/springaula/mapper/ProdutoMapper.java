package com.deloitte.springaula.mapper;

import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.dto.ProdutoResponseDTO;
import com.deloitte.springaula.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public ProdutoMapper() {}

    public Produto toEntity(ProdutoRequestDTO dto) {
        if (dto == null) return null;
        Produto p = new Produto();
        p.setNome(dto.nome());
        p.setPreco(dto.preco());
        return p;
    }

    public ProdutoResponseDTO toResponseDto(Produto p) {
        if (p == null) return null;
        return new ProdutoResponseDTO(p.getId(), p.getNome(), p.getPreco());
    }

    public void updateEntityFromDto(ProdutoRequestDTO dto, Produto entity) {
        if (dto == null || entity == null) return;
        if (dto.nome() != null) entity.setNome(dto.nome());
        if (dto.preco() != null) entity.setPreco(dto.preco());
    }
}
