package com.rodama.ecommerce.infrastructure.config;

import com.rodama.ecommerce.domain.model.enums.Rol;
import com.rodama.ecommerce.infrastructure.entity.UsuarioEntity;
import com.rodama.ecommerce.infrastructure.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final JpaUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            String correoAdmin = "admin@rodama.com";

            if (usuarioRepository.findByEmail(correoAdmin).isEmpty()) {
                UsuarioEntity admin = new UsuarioEntity();
                admin.setNombre("Administrador");
                admin.setApellido("Rodama");
                admin.setEmail(correoAdmin);
                admin.setTelefono("3001234567");
                admin.setPassword(passwordEncoder.encode("Admin123"));
                admin.setRol(Rol.ROLE_ADMIN);
                usuarioRepository.save(admin);
            }
        } catch (Exception e) {
            System.out.println("DataInitializer: " + e.getMessage());
        }
    }
}
