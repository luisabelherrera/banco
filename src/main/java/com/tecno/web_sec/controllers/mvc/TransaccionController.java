package com.tecno.web_sec.controllers.mvc;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tecno.web_sec.models.Cuenta;
import com.tecno.web_sec.models.Transaccion;
import com.tecno.web_sec.models.Usuario;
import com.tecno.web_sec.service.CuentaService;
import com.tecno.web_sec.service.TransaccionService;
import com.tecno.web_sec.service.UsuarioService;

/**
 * Controlador para gestionar las transacciones de los usuarios.
 * Proporciona métodos para listar, crear y visualizar transacciones.
 */
@Controller
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CuentaService cuentaService;

    /**
     * Lista todas las transacciones del usuario actual.
     *
     * @param model     el modelo que se pasará a la vista
     * @param principal el usuario actualmente autenticado
     * @return la vista de lista de transacciones
     */
    @GetMapping
    public String findAll(Model model, Principal principal) {
        Usuario usuarioActual = usuarioService.findByUsername(principal.getName());
        List<Transaccion> transacciones = usuarioActual.getCuentas().stream()
                .flatMap(cuenta -> transaccionService.findByCuentaId(cuenta.getId()).stream())
                .toList();

        model.addAttribute("transacciones", transacciones);
        return "user/Transaccion/transacciones";
    }

    /**
     * Muestra los detalles de una transacción específica.
     *
     * @param id    el ID de la transacción
     * @param model el modelo que se pasará a la vista
     * @return la vista de detalles de la transacción
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        Transaccion transaccion = transaccionService.findById(id);
        model.addAttribute("transaccion", transaccion);
        return "user/Transaccion/detalle-transaccion";
    }

    /**
     * Muestra el formulario para crear una nueva transacción.
     * Solo accesible para usuarios con rol de ADMIN.
     *
     * @param model     el modelo que se pasará a la vista
     * @param principal el usuario actualmente autenticado
     * @return la vista del formulario para nueva transacción
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddForm(Model model, Principal principal) {
        Transaccion transaccion = new Transaccion();

        Usuario usuarioActual = usuarioService.findByUsername(principal.getName());

        if (usuarioActual.getCuentas().isEmpty()) {
            model.addAttribute("errorMessage", "El usuario no tiene cuentas.");
            return "user/Transaccion/nueva-transaccion";
        }

        Cuenta cuentaOrigen = usuarioActual.getCuentas().get(0);
        transaccion.setCuentaOrigen(cuentaOrigen);
        transaccion.setFecha(LocalDateTime.now());

        List<Cuenta> todasLasCuentas = cuentaService.findAll();
        model.addAttribute("transaccion", transaccion);
        model.addAttribute("cuentas", todasLasCuentas);
        return "user/Transaccion/nueva-transaccion";
    }

    /**
     * Agrega una nueva transacción.
     *
     * @param transaccion la transacción a agregar
     * @param model       el modelo que se pasará a la vista
     * @return la redirección a la lista de transacciones
     */
    @PostMapping
    public String addTransaccion(@ModelAttribute("transaccion") Transaccion transaccion, Model model) {
        if (transaccion.getCuentaOrigen() == null || transaccion.getCuentaDestino() == null) {
            model.addAttribute("errorMessage", "Ambas cuentas deben ser seleccionadas.");
            List<Cuenta> cuentas = cuentaService.findAll();
            model.addAttribute("cuentas", cuentas);
            return "user/Transaccion/nueva-transaccion";
        }

        if (transaccion.getMonto() == null || transaccion.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("errorMessage", "El monto debe ser un número positivo.");
            List<Cuenta> cuentas = cuentaService.findAll();
            model.addAttribute("cuentas", cuentas);
            return "user/Transaccion/nueva-transaccion";
        }

        try {
            cuentaService.transferir(
                    transaccion.getCuentaOrigen().getId(),
                    transaccion.getCuentaDestino().getId(),
                    transaccion.getMonto());

            transaccionService.addTransaccion(transaccion);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            List<Cuenta> cuentas = cuentaService.findAll();
            model.addAttribute("cuentas", cuentas);
            return "user/Transaccion/nueva-transaccion";
        }

        return "redirect:/transacciones";
    }
}
