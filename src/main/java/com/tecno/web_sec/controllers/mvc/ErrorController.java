package com.tecno.web_sec.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para gestionar las páginas de error de la aplicación.
 */
@Controller
public class ErrorController {

    /**
     * Muestra la página de error.
     *
     * @return el nombre de la vista de error
     */
    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }
}
