package com.tecno.web_sec.controllers.mvc;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tecno.web_sec.models.Cuenta;
import com.tecno.web_sec.models.Transaccion;
import com.tecno.web_sec.service.CuentaService;
import com.tecno.web_sec.service.TransaccionService;

/**
 * Controlador para gestionar las cuentas de usuario.
 */
@Controller
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private TransaccionService transaccionService;

    /**
     * Muestra una lista de todas las cuentas.
     *
     * @param model Modelo para añadir atributos.
     * @return Nombre de la vista de cuentas.
     */
    @GetMapping
    public String findAll(Model model) {
        List<Cuenta> cuentas = cuentaService.findAll();
        model.addAttribute("cuentas", cuentas);
        return "admin/cuenta/cuentas";
    }

    /**
     * Muestra los detalles de una cuenta específica.
     *
     * @param id    Identificador de la cuenta.
     * @param model Modelo para añadir atributos.
     * @return Nombre de la vista de detalle de cuenta.
     */
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        Cuenta cuenta = cuentaService.findById(id);
        List<Transaccion> transacciones = transaccionService.findByCuentaId(id);
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("transacciones", transacciones);
        return "admin/cuenta/detalle-cuenta";
    }

    /**
     * Muestra el formulario para crear una nueva cuenta.
     *
     * @param model Modelo para añadir atributos.
     * @return Nombre de la vista para crear cuenta.
     */
    @GetMapping("/nuevo")
    public String showAddForm(Model model) {
        Transaccion transaccion = new Transaccion();
        List<Cuenta> cuentas = cuentaService.findAll();
        model.addAttribute("transaccion", transaccion);
        model.addAttribute("cuentas", cuentas);
        return "user/Transaccion/nueva-transaccion";
    }

    /**
     * Agrega una nueva cuenta.
     *
     * @param cuenta             Cuenta a agregar.
     * @param redirectAttributes Atributos para redirección.
     * @return Redirección a la lista de cuentas.
     */
    @PostMapping("/nuevo")
    public String addCuenta(@ModelAttribute("cuenta") Cuenta cuenta, RedirectAttributes redirectAttributes) {
        cuentaService.addCuenta(cuenta);
        redirectAttributes.addFlashAttribute("successMessage", "Cuenta creada con éxito.");
        return "redirect:/cuentas";
    }

    /**
     * Realiza una transferencia entre cuentas.
     *
     * @param cuentaOrigenId     ID de la cuenta origen.
     * @param cuentaDestinoId    ID de la cuenta destino.
     * @param monto              Monto a transferir.
     * @param redirectAttributes Atributos para redirección.
     * @return Redirección a la lista de cuentas.
     */
    @PostMapping("/{cuentaOrigenId}/transferir/{cuentaDestinoId}")
    public String transferir(@PathVariable("cuentaOrigenId") Long cuentaOrigenId,
            @PathVariable("cuentaDestinoId") Long cuentaDestinoId,
            @RequestParam("monto") BigDecimal monto,
            RedirectAttributes redirectAttributes) {
        try {
            cuentaService.transferir(cuentaOrigenId, cuentaDestinoId, monto);
            redirectAttributes.addFlashAttribute("successMessage", "Transferencia realizada con éxito.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/cuentas";
    }

    /**
     * Muestra el formulario para editar una cuenta existente.
     *
     * @param id    Identificador de la cuenta.
     * @param model Modelo para añadir atributos.
     * @return Nombre de la vista para editar cuenta.
     */
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Cuenta cuenta = cuentaService.findById(id);
        model.addAttribute("cuenta", cuenta);
        return "admin/cuenta/editar-cuenta";
    }

    /**
     * Actualiza una cuenta existente.
     *
     * @param cuenta Cuenta con los datos actualizados.
     * @return Redirección a la lista de cuentas.
     */
    @PostMapping("/editar")
    public String updateCuenta(@ModelAttribute("cuenta") Cuenta cuenta) {
        cuentaService.updateCuenta(cuenta);
        return "redirect:/cuentas";
    }

    /**
     * Elimina una cuenta.
     *
     * @param id Identificador de la cuenta a eliminar.
     * @return Redirección a la lista de cuentas.
     */
    @GetMapping("/eliminar/{id}")
    public String deleteCuenta(@PathVariable("id") Long id) {
        cuentaService.deleteCuenta(id);
        return "redirect:/cuentas";
    }

    /**
     * Realiza un depósito en una cuenta.
     *
     * @param id    Identificador de la cuenta.
     * @param monto Monto a depositar.
     * @return Redirección a los detalles de la cuenta.
     */
    @PostMapping("/{id}/depositar")
    public String depositar(@PathVariable("id") Long id, @RequestParam("monto") BigDecimal monto) {
        cuentaService.depositar(id, monto);
        return "redirect:/cuentas/" + id;
    }

    /**
     * Realiza un retiro de una cuenta.
     *
     * @param id    Identificador de la cuenta.
     * @param monto Monto a retirar.
     * @param model Modelo para añadir atributos.
     * @return Redirección a los detalles de la cuenta.
     */
    @PostMapping("/{id}/retirar")
    public String retirar(@PathVariable("id") Long id, @RequestParam("monto") BigDecimal monto, Model model) {
        try {
            cuentaService.retirar(id, monto);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/cuentas/" + id;
        }
        return "redirect:/cuentas/" + id;
    }
}
