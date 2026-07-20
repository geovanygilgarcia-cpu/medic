package com.exp.medic.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * No usamos Spring Security aquí (este microservicio nunca lo trajo), así
 * que este filtro hace lo mínimo necesario: exige un Bearer token válido,
 * firmado con el mismo secreto que el auth-service, y expone quién es el
 * usuario actual vía CurrentUserContext para el resto de la petición.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIJO = "Bearer ";

    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HEADER);

        if (authHeader == null || !authHeader.startsWith(PREFIJO)) {
            escribirError(response, HttpStatus.UNAUTHORIZED, "Falta el header Authorization: Bearer <token>");
            return;
        }

        String token = authHeader.substring(PREFIJO.length());

        try {
            CurrentUser usuario = jwtService.parseToken(token);
            CurrentUserContext.set(usuario);
            filterChain.doFilter(request, response);
        } catch (JwtException | IllegalArgumentException ex) {
            escribirError(response, HttpStatus.UNAUTHORIZED, "Token inválido o expirado");
        } finally {
            CurrentUserContext.clear();
        }
    }

    private void escribirError(HttpServletResponse response, HttpStatus status, String mensaje) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", mensaje);

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
