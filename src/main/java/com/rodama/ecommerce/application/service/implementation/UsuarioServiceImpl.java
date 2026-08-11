package com.rodama.ecommerce.application.service.implementation;

import com.rodama.ecommerce.application.service.UsuarioService;
import com.rodama.ecommerce.domain.model.Usuario;
import com.rodama.ecommerce.domain.model.enums.Rol;
import com.rodama.ecommerce.infrastructure.entity.UsuarioEntity;
import com.rodama.ecommerce.infrastructure.repository.JpaUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final JpaUsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(JpaUsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("El correo no puede estar vacío");
        }

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo ya existe");
        }

        if (usuario.getRol() == null) {
            usuario.setRol(Rol.ROLE_USER);
        }

        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        UsuarioEntity saved = repository.save(toEntity(usuario));
        return toModel(saved);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<Usuario> encontrarPorId(Long id) {
        return repository.findById(id)
                .map(this::toModel);
    }

    @Override
    public Optional<Usuario> encontrarPorCorreo(String correo) {
        return repository.findByEmail(correo)
                .map(this::toModel);
    }

    @Override
    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuario) {
        return repository.findById(id).map(entity -> {
            if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
                throw new RuntimeException("El correo no puede estar vacío");
            }

            if (repository.existsByEmail(usuario.getEmail())
                    && !entity.getEmail().equals(usuario.getEmail())) {
                throw new RuntimeException("El correo ya está en uso");
            }

            entity.setNombre(usuario.getNombre());
            entity.setApellido(usuario.getApellido());
            entity.setEmail(usuario.getEmail());
            entity.setTelefono(usuario.getTelefono());

            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                if (!usuario.getPassword().startsWith("$2a$")) {
                    entity.setPassword(passwordEncoder.encode(usuario.getPassword()));
                } else {
                    entity.setPassword(usuario.getPassword());
                }
            }

            if (usuario.getRol() != null) {
                entity.setRol(usuario.getRol());
            }

            UsuarioEntity actualizado = repository.save(entity);
            return toModel(actualizado);
        });
    }

    @Override
    public boolean eliminarPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        repository.deleteById(id);
        return true;
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setNombre(usuario.getNombre());
        entity.setApellido(usuario.getApellido());
        entity.setEmail(usuario.getEmail());
        entity.setTelefono(usuario.getTelefono());
        entity.setPassword(usuario.getPassword());
        entity.setRol(usuario.getRol());
        return entity;
    }

    private Usuario toModel(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNombre(entity.getNombre());
        usuario.setApellido(entity.getApellido());
        usuario.setEmail(entity.getEmail());
        usuario.setTelefono(entity.getTelefono());
        usuario.setPassword(entity.getPassword());
        usuario.setRol(entity.getRol());
        return usuario;
    }
}