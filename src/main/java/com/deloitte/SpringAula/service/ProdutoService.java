package com.deloitte.SpringAula.service;

import com.deloitte.SpringAula.Classe.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProdutoService {

    private final List<Produto> produtos = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(0);

    public List<Produto> listar() {
        return new ArrayList<>(produtos);
    }

    public Produto adicionar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("produto não pode ser nulo");
        }
        long id = nextId.incrementAndGet();
        produto.setId(id);
        produtos.add(produto);
        return produto;
    }


    public Optional<Produto> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        for (Produto p : produtos) {
            if (p.getId() != null && p.getId().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public boolean remover(Long id) {
        if (id == null) {
            return false;
        }
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            if (p != null && p.getId() != null && p.getId().equals(id)) {
                produtos.remove(i);
                return true;
            }
        }
        return false;
    }
}