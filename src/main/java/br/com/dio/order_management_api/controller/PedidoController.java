package br.com.dio.order_management_api.controller;

import br.com.dio.order_management_api.model.entity.Pedido;
import br.com.dio.order_management_api.model.enums.StatusPedido;
import br.com.dio.order_management_api.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    // Injeção de dependência pelo construtor
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // POST /pedidos — criar novo pedido (começa com status CRIADO)
    @PostMapping
    public ResponseEntity<Pedido> criar() {
        Pedido pedido = pedidoService.criar();
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    // POST /pedidos/{id}/itens — adicionar item ao pedido
    @PostMapping("/{id}/itens")
    public ResponseEntity<Pedido> adicionarItem(
            @PathVariable Long id,
            @RequestBody AdicionarItemRequest request) {

        Pedido pedido = pedidoService.adicionarItem(
                id,
                request.produtoId(),
                request.quantidade()
        );

        return ResponseEntity.ok(pedido);
    }

    // GET /pedidos — listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.listar();
        return ResponseEntity.ok(pedidos);
    }

    // GET /pedidos/{id} — buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    // GET /pedidos/status/{status} — listar pedidos por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> listarPorStatus(
            @PathVariable StatusPedido status) {

        List<Pedido> pedidos = pedidoService.listarPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }

    // Record interno para receber o corpo da requisição de adicionarItem
    record AdicionarItemRequest(Long produtoId, Integer quantidade) {}
}
