package com.tecno.web_sec.security.configuration; // Paquete donde se encuentra la clase

import java.io.IOException; // Importación para manejar excepciones de entrada/salida

import org.springframework.security.core.Authentication; // Importación para la interfaz Authentication de Spring Security
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Importación para gestionar roles de usuario
import org.springframework.security.web.authentication.AuthenticationSuccessHandler; // Importación para manejar el éxito de la autenticación

import jakarta.servlet.ServletException; // Importación para manejar excepciones de servlet
import jakarta.servlet.http.HttpServletRequest; // Importación para manejar solicitudes HTTP
import jakarta.servlet.http.HttpServletResponse; // Importación para manejar respuestas HTTP

/**
 * Clase personalizada que maneja la lógica que se ejecuta
 * cuando un usuario se autentica exitosamente.
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Método que se invoca cuando la autenticación es exitosa.
     * 
     * @param request        La solicitud HTTP
     * @param response       La respuesta HTTP
     * @param authentication El objeto de autenticación que contiene información del
     *                       usuario autenticado
     * @throws IOException      Si ocurre un error de entrada/salida
     * @throws ServletException Si ocurre un error de servlet
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Verifica si el usuario tiene el rol de administrador
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (isAdmin) {
            // Redirige a la página principal para administradores
            response.sendRedirect("/home");
        } else {
            // Redirige a la página principal para usuarios regulares
            response.sendRedirect("/home1");
        }
    }
}
