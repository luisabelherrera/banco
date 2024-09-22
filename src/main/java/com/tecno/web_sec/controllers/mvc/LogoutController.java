package com.tecno.web_sec.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controlador para gestionar el cierre de sesión de los usuarios.
 */
@Controller
public class LogoutController {

    /**
     * Maneja la solicitud de cierre de sesión.
     * Redirige al usuario a la página de inicio de sesión con un parámetro de
     * logout.
     *
     * @return una vista de redirección a la página de login
     */
    @GetMapping("/logout")
    public RedirectView logout() {
        return new RedirectView("/login?logout");
    }
}
