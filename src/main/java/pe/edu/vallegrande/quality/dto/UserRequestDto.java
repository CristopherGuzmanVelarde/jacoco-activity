package pe.edu.vallegrande.quality.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @Min(value = 0, message = "La edad debe ser mayor o igual a 0")
    @Max(value = 120, message = "La edad debe ser menor o igual a 120")
    private Integer age;
}