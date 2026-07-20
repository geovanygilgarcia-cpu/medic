package com.exp.medic.security;

public record CurrentUser(
        String id,
        String email,
        String nombreCompleto,
        String rol
) {
    public boolean esMedico() {
        return "MEDICO".equals(rol);
    }
}
