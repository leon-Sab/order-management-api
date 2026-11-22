package br.com.dio.order_management_api.service;

import br.com.dio.order_management_api.model.entity.Produto;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void cadastrar_deveSalvarERetornarProduto() {
        // given
        Produto produto = new Produto("Notebook", new BigDecimal("3500.00"));
        when(produtoRepository.save(produto)).thenReturn(produto);

        // when
        Produto resultado = produtoService.cadastrar(produto);

        // then
        assertThat(resultado).isEqualTo(produto);
        verify(produtoRepository).save(produto);
    }

    @Test
    void listar_deveRetornarListaDeProdutos() {
        // given
        List<Produto> lista = List.of(
                new Produto("Notebook", new BigDecimal("3500.00")),
                new Produto("Mouse", new BigDecimal("150.00"))
        );
        when(produtoRepository.findAll()).thenReturn(lista);

        // when
        List<Produto> resultado = produtoService.listar();

        // then
        assertThat(resultado).hasSize(2);
        verify(produtoRepository).findAll();
    }

    @Test
    void buscarPorId_deveRetornarProdutoQuandoExistir() {
        // given
        Produto produto = new Produto("Teclado", new BigDecimal("250.00"));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // when
        Produto resultado = produtoService.buscarPorId(1L);

        // then
        assertThat(resultado).isEqualTo(produto);
    }

    @Test
    void buscarPorId_deveLancarExcecaoQuandoNaoEncontrado() {
        // given
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> produtoService.buscarPorId(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("99");
    }
}
