package com.tecno.web_sec.controllers.mvc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tecno.web_sec.models.Cuenta;
import com.tecno.web_sec.models.Usuario;
import com.tecno.web_sec.service.CuentaService;
import com.tecno.web_sec.service.UsuarioService;

/**
 * Controlador para consultar cuentas de usuario.
 * Este controlador maneja las solicitudes relacionadas con la visualización de
 * cuentas
 * asociadas al usuario autenticado.
 */
@Controller
@RequestMapping("/consultas")
public class ConsultaCuentaController {

    @Autowired
    private CuentaService cuentaService; // Servicio para manejar las operaciones de la entidad Cuenta.

    @Autowired
    private UsuarioService usuarioService; // Servicio para manejar las operaciones de la entidad Usuario.

    /**
     * Lista todas las cuentas asociadas al usuario autenticado.
     *
     * @param model Objeto Model que se utiliza para añadir atributos a la vista.
     * @return El nombre de la vista que muestra la lista de cuentas.
     *         El Model es una interfaz en
     *         Spring MVC que actúa como un c
     *         ontenedor de datos que permite
     *         añadir atributos que estarán
     *         disponibles para la vista. De
     *         esta manera, el controlador puede pasar
     *         datos a la vista sin acoplamiento directo.
     */
    @GetMapping
    public String listCuentas(Model model) {
        // Obtiene la autenticación actual del usuario.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtiene el nombre de usuario autenticado.
        Usuario usuario = usuarioService.findByUsername(username); // Busca al usuario por su nombre de usuario.

        // Filtra las cuentas para obtener solo aquellas que pertenecen al usuario
        // autenticado.
        List<Cuenta> cuentas = cuentaService.findAll().stream()
                .filter(cuenta -> cuenta.getUsuario().getId().equals(usuario.getId()))
                .toList();

        model.addAttribute("cuentas", cuentas); // Añade la lista de cuentas al modelo.
        return "user/consultarcuenta/listar-cuentas"; // Devuelve el nombre de la vista.
    }

    /**
     * Muestra los detalles de una cuenta específica.
     *
     * @param id    Identificador de la cuenta a mostrar.
     * @param model Objeto Model para añadir atributos a la vista.
     * @return El nombre de la vista de detalle de la cuenta o un mensaje de error.
     */
    @GetMapping("/{id}")
    public String viewCuenta(@PathVariable("id") Long id, Model model) {
        // Obtiene la autenticación actual del usuario.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtiene el nombre de usuario autenticado.
        Usuario usuario = usuarioService.findByUsername(username); // Busca al usuario por su nombre de usuario.

        // Busca la cuenta con el ID especificado.
        Optional<Cuenta> optionalCuenta = cuentaService.findAll().stream()
                .filter(cuenta -> cuenta.getId().equals(id)) // Filtra por ID de la cuenta.
                .findFirst();

        // Si se encuentra la cuenta y pertenece al usuario autenticado:
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            if (cuenta.getUsuario().getId().equals(usuario.getId())) {
                model.addAttribute("cuenta", cuenta); // Añade la cuenta al modelo.
                return "user/consultarcuenta/detalle-cuenta"; // Devuelve la vista de detalle.
            } else {
                return "error/access-denied"; // Si el usuario no es el propietario de la cuenta, muestra acceso
                                              // denegado.
            }
        } else {
            return "error/cuenta-not-found"; // Si la cuenta no se encuentra, muestra error de cuenta no encontrada.
        }
    }
}
