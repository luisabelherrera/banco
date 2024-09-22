package com.tecno.web_sec.security.configuration; // Paquete donde se encuentra la clase

import java.net.URLEncoder; // Importación para codificar URLs
import java.nio.charset.StandardCharsets; // Importación para definir codificación de caracteres

import org.slf4j.Logger; // Importación para el registro de logs
import org.slf4j.LoggerFactory; // Importación para crear instancias de Logger
import org.springframework.context.annotation.Bean; // Importación para definir beans en el contexto de Spring
import org.springframework.context.annotation.Configuration; // Importación para indicar que esta clase es una configuración de Spring
import org.springframework.security.authentication.AuthenticationProvider; // Importación para la interfaz de proveedor de autenticación
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Importación para proveedor de autenticación basado en DAO
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Importación para construir la seguridad HTTP
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Importación para habilitar seguridad en la aplicación web
import org.springframework.security.core.userdetails.UserDetailsService; // Importación para el servicio de detalles de usuario
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Importación para el codificador de contraseña que no hace nada
import org.springframework.security.crypto.password.PasswordEncoder; // Importación para la interfaz de codificación de contraseñas
import org.springframework.security.web.SecurityFilterChain; // Importación para la cadena de filtros de seguridad
import org.springframework.security.web.access.AccessDeniedHandler; // Importación para manejar acceso denegado
import org.springframework.security.web.authentication.AuthenticationFailureHandler; // Importación para manejar fallos de autenticación
import org.springframework.security.web.authentication.AuthenticationSuccessHandler; // Importación para manejar éxito de autenticación

import com.tecno.web_sec.service.impl.UsuarioServiceImpl; // Importación del servicio de usuario

/**
 * Clase de configuración de seguridad que define las reglas y comportamientos
 * de autenticación y autorización para la aplicación web.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class); // Logger para la clase

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * 
     * @param http El objeto HttpSecurity que se utilizará para la configuración
     * @return La cadena de filtros de seguridad configurada
     * @throws Exception Si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/resources/**", "/css/**", "/error", "/public/**").permitAll() // Permite
                                                                                                                   // el
                                                                                                                   // acceso
                                                                                                                   // sin
                                                                                                                   // autenticación
                                                                                                                   // a
                                                                                                                   // estas
                                                                                                                   // URL
                        .anyRequest().authenticated()) // Requiere autenticación para cualquier otra solicitud
                .formLogin(form -> form
                        .loginPage("/login") // Especifica la página de inicio de sesión
                        .successHandler(customAuthenticationSuccessHandler()) // Define el manejador para éxito de
                                                                              // autenticación
                        .failureHandler(customAuthenticationFailureHandler()) // Define el manejador para fallo de
                                                                              // autenticación
                        .permitAll()) // Permite que todos accedan a la página de inicio de sesión
                .logout(logout -> logout
                        .logoutUrl("/logout") // Define la URL para cerrar sesión
                        .logoutSuccessUrl("/login?logout") // URL a la que se redirige después de cerrar sesión
                        .invalidateHttpSession(true) // Invalida la sesión HTTP
                        .deleteCookies("JSESSIONID") // Elimina la cookie de sesión
                        .permitAll()) // Permite que todos accedan a la función de cierre de sesión
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler())); // Define el manejador para acceso denegado

        return http.build(); // Devuelve la configuración de seguridad construida
    }

    /**
     * Proporciona un manejador de éxito de autenticación personalizado.
     * 
     * @return Un nuevo CustomAuthenticationSuccessHandler
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    /**
     * Proporciona un manejador de fallo de autenticación personalizado.
     * 
     * @return Un manejador que redirige a la página de inicio de sesión con un
     *         mensaje de error
     */
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            logger.error("Error en inicio de sesión: {}", exception.getMessage()); // Registra el error
            String errorMessage = "Error en inicio de sesión"; // Mensaje de error por defecto
            if (exception.getMessage() != null) {
                errorMessage = exception.getMessage(); // Utiliza el mensaje de excepción si está disponible
            }
            String encodedError = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString()); // Codifica el
                                                                                                      // mensaje de
                                                                                                      // error para URL
            response.sendRedirect("/login?error=" + encodedError); // Redirige con el mensaje de error codificado
        };
    }

    /**
     * Proporciona un manejador personalizado para el acceso denegado.
     * 
     * @return Un manejador que redirige a la página de error
     */
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/error"); // Redirige a la página de error
        };
    }

    /**
     * Proporciona un proveedor de autenticación que utiliza un servicio de usuario.
     * 
     * @return Un DaoAuthenticationProvider configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioService()); // Establece el servicio de usuario
        authProvider.setPasswordEncoder(passwordEncoder()); // Establece el codificador de contraseñas
        return authProvider; // Devuelve el proveedor de autenticación configurado
    }

    /**
     * Proporciona el servicio de detalles de usuario.
     * 
     * @return Un nuevo UsuarioServiceImpl
     */
    @Bean
    public UserDetailsService usuarioService() {
        return new UsuarioServiceImpl();
    }

    /**
     * Proporciona un codificador de contraseñas que no realiza codificación.
     * 
     * @return Un codificador de contraseñas NoOp
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Retorna un codificador que no hace nada
    }
}
