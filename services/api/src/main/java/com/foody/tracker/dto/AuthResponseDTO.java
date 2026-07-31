package com.foody.tracker.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponseDTO {
    private String token;
    private String nome;
    private String email;
}
