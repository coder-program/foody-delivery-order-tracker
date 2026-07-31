package com.foody.tracker.service;

import com.foody.tracker.dto.AuthResponseDTO;
import com.foody.tracker.dto.LoginRequestDTO;
import com.foody.tracker.dto.RegisterRequestDTO;
import com.foody.tracker.entity.AppUser;
import com.foody.tracker.exception.BusinessException;
import com.foody.tracker.repository.UserRepository;
import com.foody.tracker.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        AppUser user = AppUser.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponseDTO.builder()
                .token(token)
                .nome(user.getNome())
                .email(user.getEmail())
                .build();
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Credenciais inválidas"));

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponseDTO.builder()
                .token(token)
                .nome(user.getNome())
                .email(user.getEmail())
                .build();
    }
}
