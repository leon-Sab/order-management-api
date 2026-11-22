package br.com.dio.order_management_api.service;

import br.com.dio.order_management_api.model.entity.Pedido;
import br.com.dio.order_management_api.model.entity.Produto;
import br.com.dio.order_management_api.model.enums.StatusPedido;
import br.com.dio.order_management_api.repository.PedidoRepository;
import br.com.dio.order_management_api.repository.ProdutoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    // Injeção de dependência pelo construtor
    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    // Criar um novo pedido (sempre começa com status CRIADO)
    @Transactional
    public Pedido criar() {
        Pedido pedido = new Pedido(StatusPedido.CRIADO);
        return pedidoRepository.save(pedido);
    }

    // Adicionar um item ao pedido (busca produto e delega à entidade)
    @Transactional
    public Pedido adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {

        Pedido pedido = buscarPorId(pedidoId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() ->
                        new NoSuchElementException("Produto não encontrado: " + produtoId));

        pedido.adicionarItem(produto, quantidade);

        // O cascade garante que o ItemPedido também será salvo
        return pedidoRepository.save(pedido);
    }

    // Listar todos os pedidos
    @Transactional(readOnly = true)
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    // Buscar pedido pelo ID
    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Pedido não encontrado: " + id));
    }

    // Listar pedidos por status
    @Transactional(readOnly = true)
    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }
}
