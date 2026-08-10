package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.AuthRequestDTO;
import com.rodama.ecommerce.application.dto.AuthResponseDTO;
import com.rodama.ecommerce.application.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 🔥 Usuario autenticado real
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String email = userDetails.getUsername();

        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority(); // ROLE_ADMIN

        String token = jwtUtil.generateToken(email, role);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
