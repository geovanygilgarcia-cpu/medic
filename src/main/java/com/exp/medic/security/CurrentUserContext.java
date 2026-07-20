package com.exp.medic.security;

/**
 * Como este microservicio no trae Spring Security completo (solo el filtro
 * de abajo), usamos un ThreadLocal simple para exponer "quién soy" durante
 * la petición. Tomcat atiende cada request en un solo hilo, así que esto es
 * seguro; solo hay que limpiarlo siempre en el finally del filtro.
 */
public final class CurrentUserContext {

    private static final ThreadLocal<CurrentUser> HOLDER = new ThreadLocal<>();

    private CurrentUserContext() {
    }

    public static void set(CurrentUser usuario) {
        HOLDER.set(usuario);
    }

    public static CurrentUser get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }
}
