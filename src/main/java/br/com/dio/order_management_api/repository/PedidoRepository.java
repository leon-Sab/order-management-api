package br.com.dio.order_management_api.repository;

import br.com.dio.order_management_api.model.entity.Pedido;
import br.com.dio.order_management_api.model.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Busca todos os pedidos com um determinado status
    List<Pedido> findByStatus(StatusPedido status);
}
