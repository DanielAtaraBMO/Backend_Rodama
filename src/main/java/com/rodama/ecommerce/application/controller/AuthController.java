package com.rodama.ecommerce.application.controller;

import com.rodama.ecommerce.application.dto.AuthRequestDTO;
import com.rodama.ecommerce.application.dto.AuthResponseDTO;
import com.rodama.ecommerce.application.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5173", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
@Tag(name = "Autenticación", description = "Endpoints para login y generación de tokens JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica al usuario con email y contraseña, retorna un token JWT para usar en los demás endpoints."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token JWT"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();
        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();

        String token = jwtUtil.generateToken(email, role);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
