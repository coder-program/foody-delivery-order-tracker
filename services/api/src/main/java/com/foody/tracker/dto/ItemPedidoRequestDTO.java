package com.foody.tracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoRequestDTO {

    @NotBlank(message = "Nome do item é obrigatório")
    private String nome;

    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;
}
