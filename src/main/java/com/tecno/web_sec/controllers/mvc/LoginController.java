package com.tecno.web_sec.controllers.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

/**
 * Controlador para gestionar el inicio de sesión de los usuarios.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Muestra el formulario de inicio de sesión.
     *
     * @param error  mensaje de error si el inicio de sesión falla
     * @param logout mensaje de información si el usuario ha cerrado sesión
     * @return un objeto ModelAndView que contiene la vista del formulario de inicio
     *         de sesión
     */
    @GetMapping("/login")
    public ModelAndView showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView modelAndView = new ModelAndView("login");

        if (error != null) {
            modelAndView.addObject("errorMessage", error);
        }

        if (logout != null) {
            modelAndView.addObject("infoMessage", "Sesión cerrada exitosamente.");
        }

        return modelAndView;
    }

    /**
     * Maneja el envío del formulario de inicio de sesión.
     *
     * @param username el nombre de usuario ingresado
     * @param password la contraseña ingresada
     * @param session  la sesión HTTP del usuario
     * @return un objeto ModelAndView que redirige a la página de inicio o vuelve al
     *         formulario de inicio de sesión en caso de error
     */
    @PostMapping("/login")
    public ModelAndView postMethodName(@RequestParam String username, @RequestParam String password,
            HttpSession session) {

        logger.info("Usuario => {}", username);

        if (username.equals("error")) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Usuario o contraseña incorrectos.");
            return modelAndView;
        }
        session.setAttribute("username", username);
        logger.info("Redirect to home");

        return new ModelAndView("redirect:/home");
    }
}
