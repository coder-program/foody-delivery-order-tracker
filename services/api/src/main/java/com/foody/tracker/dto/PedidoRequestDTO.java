package com.foody.tracker.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequestDTO {

    @NotBlank(message = "Cliente é obrigatório")
    private String cliente;

    @NotBlank(message = "Endereço é obrigatório")
    private String enderecoEntrega;

    @Valid
    @NotEmpty(message = "Pedido deve conter ao menos um item")
    private List<ItemPedidoRequestDTO> listaItens;
}
