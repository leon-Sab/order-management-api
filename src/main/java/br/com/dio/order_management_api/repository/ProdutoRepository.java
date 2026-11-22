package br.com.dio.order_management_api.repository;

import br.com.dio.order_management_api.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
