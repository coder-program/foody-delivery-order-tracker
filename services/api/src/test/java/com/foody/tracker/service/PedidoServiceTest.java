package com.foody.tracker.service;

import com.foody.tracker.dto.ItemPedidoRequestDTO;
import com.foody.tracker.dto.PedidoRequestDTO;
import com.foody.tracker.dto.PedidoResponseDTO;
import com.foody.tracker.entity.ItemPedido;
import com.foody.tracker.entity.Pedido;
import com.foody.tracker.entity.StatusPedido;
import com.foody.tracker.exception.ResourceNotFoundException;
import com.foody.tracker.mapper.PedidoMapper;
import com.foody.tracker.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedidoBase() {
        LocalDateTime now = LocalDateTime.now();
        return Pedido.builder()
                .id(1L)
                .cliente("João")
                .enderecoEntrega("Rua A, 1")
                .status(StatusPedido.RECEBIDO)
                .dataCriacao(now)
                .dataAtualizacao(now)
                .listaItens(new ArrayList<>(List.of(
                        ItemPedido.builder().id(1L).nome("Pizza").quantidade(2).build()
                )))
                .build();
    }

    private PedidoResponseDTO responseBase(Pedido p) {
        return PedidoResponseDTO.builder()
                .id(p.getId())
                .cliente(p.getCliente())
                .enderecoEntrega(p.getEnderecoEntrega())
                .status(p.getStatus())
                .dataCriacao(p.getDataCriacao())
                .dataAtualizacao(p.getDataAtualizacao())
                .listaItens(List.of())
                .build();
    }

    @Test
    void listarTodos_deveRetornarListaDePedidos() {
        Pedido pedido = pedidoBase();
        PedidoResponseDTO dto = responseBase(pedido);

        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));
        when(pedidoMapper.toResponse(pedido)).thenReturn(dto);

        List<PedidoResponseDTO> result = pedidoService.listarTodos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCliente()).isEqualTo("João");
    }

    @Test
    void listarTodos_semPedidos_deveRetornarListaVazia() {
        when(pedidoRepository.findAll()).thenReturn(List.of());

        List<PedidoResponseDTO> result = pedidoService.listarTodos();

        assertThat(result).isEmpty();
    }

    @Test
    void buscarPorId_comIdExistente_deveRetornarPedido() {
        Pedido pedido = pedidoBase();
        PedidoResponseDTO dto = responseBase(pedido);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoMapper.toResponse(pedido)).thenReturn(dto);

        PedidoResponseDTO result = pedidoService.buscarPorId(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCliente()).isEqualTo("João");
    }

    @Test
    void buscarPorId_comIdInexistente_deveLancarResourceNotFoundException() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.buscarPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pedido não encontrado");
    }

    @Test
    void criar_deveSalvarERetornarPedido() {
        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO();
        itemDTO.setNome("Burguer");
        itemDTO.setQuantidade(1);

        PedidoRequestDTO request = new PedidoRequestDTO();
        request.setCliente("Maria");
        request.setEnderecoEntrega("Av. B, 2");
        request.setListaItens(List.of(itemDTO));

        Pedido pedido = pedidoBase();
        PedidoResponseDTO dto = responseBase(pedido);

        when(pedidoMapper.toEntity(request)).thenReturn(pedido);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        when(pedidoMapper.toResponse(pedido)).thenReturn(dto);

        PedidoResponseDTO result = pedidoService.criar(request);

        assertThat(result).isNotNull();
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void atualizarStatus_comIdExistente_deveAlterarStatus() {
        Pedido pedido = pedidoBase();
        Pedido atualizado = pedidoBase();
        atualizado.setStatus(StatusPedido.EM_PREPARO);

        PedidoResponseDTO dto = PedidoResponseDTO.builder()
                .id(1L)
                .cliente("João")
                .enderecoEntrega("Rua A, 1")
                .status(StatusPedido.EM_PREPARO)
                .dataCriacao(pedido.getDataCriacao())
                .dataAtualizacao(pedido.getDataAtualizacao())
                .listaItens(List.of())
                .build();

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(atualizado);
        when(pedidoMapper.toResponse(atualizado)).thenReturn(dto);

        PedidoResponseDTO result = pedidoService.atualizarStatus(1L, StatusPedido.EM_PREPARO);

        assertThat(result.getStatus()).isEqualTo(StatusPedido.EM_PREPARO);
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    void atualizarStatus_comIdInexistente_deveLancarResourceNotFoundException() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.atualizarStatus(99L, StatusPedido.ENTREGUE))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Pedido não encontrado");
    }
}
