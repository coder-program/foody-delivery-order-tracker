package com.foody.tracker.controller;

import com.foody.tracker.dto.PedidoRequestDTO;
import com.foody.tracker.dto.PedidoResponseDTO;
import com.foody.tracker.dto.PedidoStatusUpdateRequestDTO;
import com.foody.tracker.service.PedidoService;
import com.foody.tracker.util.ApiPaths;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.PEDIDOS_BASE)
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criar(request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id,
                                                             @Valid @RequestBody PedidoStatusUpdateRequestDTO request) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, request.getStatus()));
    }
}
