package com.deloitte.springaula.service.impl;

import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.dto.ProdutoResponseDTO;
import com.deloitte.springaula.model.Produto;
import com.deloitte.springaula.repository.ProdutoRepository;
import com.deloitte.springaula.exception.ProdutoNotFoundException;
import com.deloitte.springaula.mapper.ProdutoMapper;
import com.deloitte.springaula.service.ProdutoService;
import com.deloitte.springaula.service.rules.ProdutoBusinessRule;
import com.deloitte.springaula.exception.BusinessRuleViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;
    private final List<ProdutoBusinessRule> rules;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper, List<ProdutoBusinessRule> rules) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
        this.rules = rules;
    }

    @Override
    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toResponseDto)
                .toList();
    }

    private void validateRules(Long id, ProdutoRequestDTO dto) {
        Map<String, String> errors = new HashMap<>();
        for (ProdutoBusinessRule rule : rules) {
            Map<String, String> r = rule.validate(id, dto);
            if (r != null && !r.isEmpty()) errors.putAll(r);
        }
        if (!errors.isEmpty()) throw new BusinessRuleViolationException(errors);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoDto) {
        validateRules(null, produtoDto);
        Produto p = produtoMapper.toEntity(produtoDto);
        Produto salvo = produtoRepository.save(p);
        return produtoMapper.toResponseDto(salvo);
    }

    @Override
    public ProdutoResponseDTO buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(produtoMapper::toResponseDto)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    @Override
    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoDto) {
        Produto atual = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        validateRules(id, produtoDto);
        produtoMapper.updateEntityFromDto(produtoDto, atual);

        Produto salvo = produtoRepository.save(atual);
        return produtoMapper.toResponseDto(salvo);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException(id);
        }
        produtoRepository.deleteById(id);
    }
}