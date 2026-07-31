package com.foody.tracker.dto;

import com.foody.tracker.entity.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoStatusUpdateRequestDTO {

    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;
}
