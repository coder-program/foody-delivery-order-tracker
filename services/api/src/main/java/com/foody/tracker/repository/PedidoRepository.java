package com.foody.tracker.repository;

import com.foody.tracker.entity.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Override
    @EntityGraph(attributePaths = "listaItens")
    List<Pedido> findAll();

    @Override
    @EntityGraph(attributePaths = "listaItens")
    Optional<Pedido> findById(Long id);
}
