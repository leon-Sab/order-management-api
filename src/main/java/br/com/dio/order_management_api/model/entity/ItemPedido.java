package br.com.dio.order_management_api.model.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precoUnitario;

    protected ItemPedido() {
        // Construtor exigido pelo JPA
    }

    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade) {

        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException(
                "A quantidade deve ser maior que zero"
            );
        }

        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;

        // Guarda o preço do produto no momento da compra
        this.precoUnitario = produto.getPreco();
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(
            BigDecimal.valueOf(quantidade)
        );
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }
}