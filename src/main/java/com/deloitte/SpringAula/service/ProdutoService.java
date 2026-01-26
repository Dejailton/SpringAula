package com.deloitte.SpringAula.service;

import com.deloitte.SpringAula.Entity.Produto;
import com.deloitte.SpringAula.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto adicionar(Produto produto) {
        return produtoRepository.save(produto);
    }


    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto atualizar(Long id, Produto produto) {
        Optional<Produto> existente = produtoRepository.findById(id);
        if (!existente.isPresent()) {
            return null;
        }
        Produto atual = existente.get();
        if (produto.getNome() != null) {
            atual.setNome(produto.getNome());
        }
        if (produto.getPreco() != null) {
            atual.setPreco(produto.getPreco());
        }
        return produtoRepository.save(atual);
    }

    public boolean remover(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}