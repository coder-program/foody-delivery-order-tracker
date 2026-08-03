package com.foody.tracker.mapper;

import com.foody.tracker.dto.ItemPedidoRequestDTO;
import com.foody.tracker.dto.ItemPedidoResponseDTO;
import com.foody.tracker.dto.PedidoRequestDTO;
import com.foody.tracker.dto.PedidoResponseDTO;
import com.foody.tracker.entity.ItemPedido;
import com.foody.tracker.entity.Pedido;
import com.foody.tracker.entity.StatusPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoMapperTest {

    private PedidoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PedidoMapper();
    }

    @Test
    void toEntity_deveMapearCamposCorretamente() {
        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO();
        itemDTO.setNome("Pizza");
        itemDTO.setQuantidade(2);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setCliente("João");
        dto.setEnderecoEntrega("Rua A, 1");
        dto.setListaItens(List.of(itemDTO));

        Pedido pedido = mapper.toEntity(dto);

        assertThat(pedido.getCliente()).isEqualTo("João");
        assertThat(pedido.getEnderecoEntrega()).isEqualTo("Rua A, 1");
        assertThat(pedido.getListaItens()).hasSize(1);
        assertThat(pedido.getListaItens().get(0).getNome()).isEqualTo("Pizza");
        assertThat(pedido.getListaItens().get(0).getQuantidade()).isEqualTo(2);
    }

    @Test
    void toResponse_deveMapearCamposCorretamente() {
        LocalDateTime now = LocalDateTime.now();

        ItemPedido item = ItemPedido.builder()
                .id(1L)
                .nome("Burguer")
                .quantidade(1)
                .build();

        Pedido pedido = Pedido.builder()
                .id(10L)
                .cliente("Maria")
                .enderecoEntrega("Av. B, 2")
                .status(StatusPedido.RECEBIDO)
                .dataCriacao(now)
                .dataAtualizacao(now)
                .listaItens(new java.util.ArrayList<>(List.of(item)))
                .build();

        PedidoResponseDTO response = mapper.toResponse(pedido);

        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getCliente()).isEqualTo("Maria");
        assertThat(response.getEnderecoEntrega()).isEqualTo("Av. B, 2");
        assertThat(response.getStatus()).isEqualTo(StatusPedido.RECEBIDO);
        assertThat(response.getDataCriacao()).isEqualTo(now);
        assertThat(response.getDataAtualizacao()).isEqualTo(now);
        assertThat(response.getListaItens()).hasSize(1);

        ItemPedidoResponseDTO itemResponse = response.getListaItens().get(0);
        assertThat(itemResponse.getId()).isEqualTo(1L);
        assertThat(itemResponse.getNome()).isEqualTo("Burguer");
        assertThat(itemResponse.getQuantidade()).isEqualTo(1);
    }

    @Test
    void toEntity_comMultiplosItens_deveMapearTodos() {
        ItemPedidoRequestDTO item1 = new ItemPedidoRequestDTO();
        item1.setNome("Batata");
        item1.setQuantidade(3);

        ItemPedidoRequestDTO item2 = new ItemPedidoRequestDTO();
        item2.setNome("Suco");
        item2.setQuantidade(2);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setCliente("Carlos");
        dto.setEnderecoEntrega("Rua C, 3");
        dto.setListaItens(List.of(item1, item2));

        Pedido pedido = mapper.toEntity(dto);

        assertThat(pedido.getListaItens()).hasSize(2);
        assertThat(pedido.getListaItens().get(0).getNome()).isEqualTo("Batata");
        assertThat(pedido.getListaItens().get(1).getNome()).isEqualTo("Suco");
    }
}
