package com.exp.medic.dto.paciente.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PacienteDTO(

        String expediente,

        @NotBlank(message = "El nombre completo es obligatorio")
        String nombreCompleto,

        String telefono,

        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        LocalDate fechaNacimiento,

        @Email(message = "El correo no tiene un formato válido")
        String email,

        String genero,

        String contactoEmergencia
) {
}
