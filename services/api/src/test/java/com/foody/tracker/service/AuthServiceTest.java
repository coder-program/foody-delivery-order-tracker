package com.foody.tracker.service;

import com.foody.tracker.dto.AuthResponseDTO;
import com.foody.tracker.dto.LoginRequestDTO;
import com.foody.tracker.dto.RegisterRequestDTO;
import com.foody.tracker.entity.AppUser;
import com.foody.tracker.exception.BusinessException;
import com.foody.tracker.repository.UserRepository;
import com.foody.tracker.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_deveCriarUsuarioERetornarToken() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNome("João");
        request.setEmail("joao@email.com");
        request.setSenha("123456");

        when(userRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("hashed");
        when(userRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));
        when(jwtService.generateToken("joao@email.com")).thenReturn("token-jwt");

        AuthResponseDTO response = authService.register(request);

        assertThat(response.getToken()).isEqualTo("token-jwt");
        assertThat(response.getNome()).isEqualTo("João");
        assertThat(response.getEmail()).isEqualTo("joao@email.com");
        verify(userRepository).save(any(AppUser.class));
    }

    @Test
    void register_comEmailJaCadastrado_deveLancarBusinessException() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setNome("João");
        request.setEmail("joao@email.com");
        request.setSenha("123456");

        when(userRepository.existsByEmail("joao@email.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Email já cadastrado");
    }

    @Test
    void login_comCredenciaisValidas_deveRetornarToken() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("maria@email.com");
        request.setSenha("senha123");

        AppUser user = AppUser.builder()
                .nome("Maria")
                .email("maria@email.com")
                .senha("hashed")
                .build();

        when(userRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("maria@email.com")).thenReturn("token-maria");

        AuthResponseDTO response = authService.login(request);

        assertThat(response.getToken()).isEqualTo("token-maria");
        assertThat(response.getNome()).isEqualTo("Maria");
        assertThat(response.getEmail()).isEqualTo("maria@email.com");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_comUsuarioNaoEncontrado_deveLancarBusinessException() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("naoexiste@email.com");
        request.setSenha("qualquer");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Credenciais inválidas");
    }
}
