package com.tecno.web_sec.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para gestionar las páginas de inicio de la aplicación.
 */
@Controller
public class HomeController {

    /**
     * Muestra la página de inicio para administradores.
     *
     * @return el nombre de la vista de inicio para administradores
     */
    @GetMapping("/home")
    public String showHomePage() {
        return "admin/home";
    }

    /**
     * Muestra la página de inicio para usuarios.
     *
     * @return el nombre de la vista de inicio para usuarios
     */
    @GetMapping("/home1")
    public String showHomePage1() {
        return "user/home1";
    }
}
