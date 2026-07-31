package com.foody.tracker.dto;

import com.foody.tracker.entity.StatusPedido;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PedidoResponseDTO {
    private Long id;
    private String cliente;
    private String enderecoEntrega;
    private StatusPedido status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<ItemPedidoResponseDTO> listaItens;
}
