package com.deloitte.springaula.service;

import com.deloitte.springaula.dto.ProdutoRequestDTO;
import com.deloitte.springaula.dto.ProdutoResponseDTO;
import com.deloitte.springaula.model.Produto;
import com.deloitte.springaula.repository.ProdutoRepository;
import com.deloitte.springaula.exception.ProdutoNotFoundException;
import com.deloitte.springaula.mapper.ProdutoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoDto) {
        Produto p = ProdutoMapper.toEntity(produtoDto);
        Produto salvo = produtoRepository.save(p);
        return ProdutoMapper.toResponseDto(salvo);
    }

    @Override
    public ProdutoResponseDTO buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(ProdutoMapper::toResponseDto)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    @Override
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoDto) {
        Produto atual = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        ProdutoMapper.updateEntityFromDto(produtoDto, atual);

        Produto salvo = produtoRepository.save(atual);
        return ProdutoMapper.toResponseDto(salvo);
    }

    @Override
    public void remover(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException(id);
        }
        produtoRepository.deleteById(id);
    }
}