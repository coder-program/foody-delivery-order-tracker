package com.foody.tracker.service;

import com.foody.tracker.dto.PedidoRequestDTO;
import com.foody.tracker.dto.PedidoResponseDTO;
import com.foody.tracker.entity.Pedido;
import com.foody.tracker.entity.StatusPedido;
import com.foody.tracker.exception.ResourceNotFoundException;
import com.foody.tracker.mapper.PedidoMapper;
import com.foody.tracker.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        return pedidoMapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO request) {
        Pedido pedido = pedidoMapper.toEntity(request);
        Pedido salvo = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(salvo);
    }

    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        pedido.setStatus(status);
        Pedido atualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(atualizado);
    }
}
