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
    public void run(String... args) throws Exception {
        String correoAdmin = "admin@rodama.com";

        boolean existe = usuarioRepository.findByEmail(correoAdmin).isPresent();

        if (!existe) {
            UsuarioEntity admin = new UsuarioEntity();

            admin.setNombre("Administrador");
            admin.setEmail(correoAdmin);
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setRol(Rol.ROLE_ADMIN);; // IMPORTANTE

            usuarioRepository.save(admin);

            System.out.println("Admin creado correctamente");
        } else {
            System.out.println("Admin ya existe");
        }
    }
}
