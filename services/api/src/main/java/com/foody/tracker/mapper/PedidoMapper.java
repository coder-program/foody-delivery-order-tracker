package com.foody.tracker.mapper;

import com.foody.tracker.dto.ItemPedidoRequestDTO;
import com.foody.tracker.dto.ItemPedidoResponseDTO;
import com.foody.tracker.dto.PedidoRequestDTO;
import com.foody.tracker.dto.PedidoResponseDTO;
import com.foody.tracker.entity.ItemPedido;
import com.foody.tracker.entity.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido toEntity(PedidoRequestDTO dto) {
        Pedido pedido = Pedido.builder()
                .cliente(dto.getCliente())
                .enderecoEntrega(dto.getEnderecoEntrega())
                .build();

        List<ItemPedido> itens = dto.getListaItens().stream()
                .map(this::toEntityItem)
                .toList();
        pedido.replaceItens(itens);
        return pedido;
    }

    public PedidoResponseDTO toResponse(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .cliente(pedido.getCliente())
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .status(pedido.getStatus())
                .dataCriacao(pedido.getDataCriacao())
                .dataAtualizacao(pedido.getDataAtualizacao())
                .listaItens(pedido.getListaItens().stream().map(this::toResponseItem).toList())
                .build();
    }

    private ItemPedido toEntityItem(ItemPedidoRequestDTO itemDTO) {
        return ItemPedido.builder()
                .nome(itemDTO.getNome())
                .quantidade(itemDTO.getQuantidade())
                .build();
    }

    private ItemPedidoResponseDTO toResponseItem(ItemPedido item) {
        return ItemPedidoResponseDTO.builder()
                .id(item.getId())
                .nome(item.getNome())
                .quantidade(item.getQuantidade())
                .build();
    }
}
