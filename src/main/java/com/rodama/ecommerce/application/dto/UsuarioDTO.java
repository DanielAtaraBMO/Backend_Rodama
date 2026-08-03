package com.rodama.ecommerce.application.dto;

import com.rodama.ecommerce.domain.model.Usuario;
import com.rodama.ecommerce.domain.model.enums.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 20, message = "El nombre debe contener mínimo 3 a 20 caracteres")
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 3, max = 20, message = "El apellido debe contener mínimo 3 a 20 caracteres")
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    @Schema(description = "Correo electrónico del usuario", example = "juan@email.com")
    private String correo;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono debe contener entre 7 y 15 dígitos")
    @Schema(description = "Número de teléfono", example = "3001234567")
    private String telefono;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 100, message = "La contraseña debe tener mínimo 6 caracteres")
    @Schema(description = "Contraseña del usuario", example = "password123")
    private String contraseña;

    @NotNull(message = "El rol es obligatorio")
    @Schema(description = "Rol del usuario", example = "ADMIN")
    private Rol rol;

    public static UsuarioDTO fromEntity(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setContraseña(usuario.getContraseña());
        dto.setRol(usuario.getRol());
        return dto;
    }

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNombre(this.nombre);
        usuario.setApellido(this.apellido);
        usuario.setCorreo(this.correo);
        usuario.setTelefono(this.telefono);
        usuario.setContraseña(this.contraseña);
        usuario.setRol(this.rol);
        return usuario;
    }


}
