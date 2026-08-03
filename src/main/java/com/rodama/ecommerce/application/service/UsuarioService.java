package com.rodama.ecommerce.application.service;

import com.rodama.ecommerce.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    List<Usuario> obtenerTodos();
    Optional<Usuario> encontrarPorId(Long id);
    Optional<Usuario> encontrarPorCorreo(String correo);
    Optional<Usuario> actualizarUsuario(Long id, Usuario usuario);
    boolean eliminarPorId(Long id);

}
