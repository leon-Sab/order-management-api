package br.com.dio.order_management_api.service;

import br.com.dio.order_management_api.model.entity.Pedido;
import br.com.dio.order_management_api.model.entity.Produto;
import br.com.dio.order_management_api.model.enums.StatusPedido;
import br.com.dio.order_management_api.repository.PedidoRepository;
import br.com.dio.order_management_api.repository.ProdutoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void criar_deveCriarPedidoComStatusCriado() {
        // given
        Pedido pedido = new Pedido(StatusPedido.CRIADO);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // when
        Pedido resultado = pedidoService.criar();

        // then
        assertThat(resultado.getStatus()).isEqualTo(StatusPedido.CRIADO);
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    void adicionarItem_deveAdicionarItemAoPedidoExistente() {
        // given
        Pedido pedido = new Pedido(StatusPedido.CRIADO);
        Produto produto = new Produto("Monitor", new BigDecimal("1200.00"));

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        // when
        Pedido resultado = pedidoService.adicionarItem(1L, 1L, 2);

        // then
        assertThat(resultado.getItens()).hasSize(1);
        assertThat(resultado.getItens().get(0).getQuantidade()).isEqualTo(2);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void adicionarItem_deveLancarExcecaoSePedidoNaoExistir() {
        // given
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> pedidoService.adicionarItem(99L, 1L, 1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("99");
    }

    @Test
    void adicionarItem_deveLancarExcecaoSeProdutoNaoExistir() {
        // given
        Pedido pedido = new Pedido(StatusPedido.CRIADO);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> pedidoService.adicionarItem(1L, 99L, 1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("99");
    }

    @Test
    void listarPorStatus_deveRetornarApenasPedidosComStatusInformado() {
        // given
        List<Pedido> pedidos = List.of(new Pedido(StatusPedido.CRIADO));
        when(pedidoRepository.findByStatus(StatusPedido.CRIADO)).thenReturn(pedidos);

        // when
        List<Pedido> resultado = pedidoService.listarPorStatus(StatusPedido.CRIADO);

        // then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getStatus()).isEqualTo(StatusPedido.CRIADO);
    }
}
