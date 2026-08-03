package com.rodama.ecommerce.application.service.implementation;

import com.rodama.ecommerce.application.service.UsuarioService;
import com.rodama.ecommerce.infrastructure.entity.UsuarioEntity;
import com.rodama.ecommerce.infrastructure.repository.JpaUsuarioRepository;
import com.rodama.ecommerce.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final JpaUsuarioRepository repository;

    public UsuarioServiceImpl(JpaUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {

        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            throw new RuntimeException("El correo no puede estar vacío");
        }

        if (repository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya existe");
        }

        UsuarioEntity saved = repository.save(toEntity(usuario));
        return toModel(saved);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<UsuarioEntity> usuarios = repository.findAll();

        return usuarios.stream()
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
        return repository.findByCorreo(correo)
                .map(this::toModel);
    }

    @Override
    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuario) {

        return repository.findById(id).map(entity -> {

            if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
                throw new RuntimeException("El correo no puede estar vacío");
            }

            if (repository.existsByCorreo(usuario.getCorreo())
                    && !entity.getCorreo().equals(usuario.getCorreo())) {
                throw new RuntimeException("El correo ya está en uso");
            }

            entity.setNombre(usuario.getNombre());
            entity.setApellido(usuario.getApellido());
            entity.setCorreo(usuario.getCorreo());
            entity.setTelefono(usuario.getTelefono());
            entity.setContraseña(usuario.getContraseña());
            entity.setRol(usuario.getRol());

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

    // 🔹 Mappers

    private UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setNombre(usuario.getNombre());
        entity.setApellido(usuario.getApellido());
        entity.setCorreo(usuario.getCorreo());
        entity.setTelefono(usuario.getTelefono());
        entity.setContraseña(usuario.getContraseña());
        entity.setRol(usuario.getRol());
        return entity;
    }

    private Usuario toModel(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNombre(entity.getNombre());
        usuario.setApellido(entity.getApellido());
        usuario.setCorreo(entity.getCorreo());
        usuario.setTelefono(entity.getTelefono());
        usuario.setContraseña(entity.getContraseña());
        usuario.setRol(entity.getRol());
        return usuario;
    }
}