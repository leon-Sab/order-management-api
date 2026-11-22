package br.com.dio.order_management_api.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.dio.order_management_api.model.enums.StatusPedido;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @OneToMany(
        mappedBy = "pedido",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ItemPedido> itens = new ArrayList<>();

    protected Pedido() {
        // Construtor exigido pelo JPA
    }

    public Pedido(StatusPedido status) {
        this.dataCriacao = LocalDateTime.now();
        this.status = status;
    }

    public void adicionarItem(Produto produto, Integer quantidade) {

        ItemPedido item = new ItemPedido(
            this,
            produto,
            quantidade
        );

        itens.add(item);
    }

    public BigDecimal calcularTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : itens) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<ItemPedido> getItens() {
        return List.copyOf(itens);
    }
}