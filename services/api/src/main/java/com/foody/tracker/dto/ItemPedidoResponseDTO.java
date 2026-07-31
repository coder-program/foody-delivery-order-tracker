package com.foody.tracker.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemPedidoResponseDTO {
    private Long id;
    private String nome;
    private Integer quantidade;
}
