package com.rodama.ecommerce.application.service;

import com.rodama.ecommerce.infrastructure.entity.UsuarioEntity;
import com.rodama.ecommerce.infrastructure.repository.JpaUsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JpaUsuarioRepository usuarioRepository;

    public CustomUserDetailsService(JpaUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        String role = usuario.getRol() != null ? usuario.getRol().name() : "ROLE_USER";

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}