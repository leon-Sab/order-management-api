package br.com.dio.order_management_api.service;

import br.com.dio.order_management_api.model.entity.Produto;
import br.com.dio.order_management_api.repository.ProdutoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    // Injeção de dependência pelo construtor
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Cadastrar produto
    public Produto cadastrar(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Listar todos os produtos
    @Transactional(readOnly = true)
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    // Buscar produto pelo ID
    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Produto não encontrado: " + id));
    }
}