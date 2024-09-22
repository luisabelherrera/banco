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

import com.tecno.web_sec.models.Cuenta;
import com.tecno.web_sec.models.Usuario;
import com.tecno.web_sec.service.UsuarioService;

/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios.
 * Proporciona métodos para listar, crear, editar y eliminar usuarios y sus
 * cuentas.
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Lista todos los usuarios.
     *
     * @param model el modelo que se pasará a la vista
     * @return la vista de lista de usuarios
     */
    @GetMapping
    public String listUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuario/usuarios";
    }

    /**
     * Muestra los detalles de un usuario específico.
     *
     * @param id    el ID del usuario
     * @param model el modelo que se pasará a la vista
     * @return la vista de detalles del usuario
     */
    @GetMapping("/{id}")
    public String viewUsuario(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        List<Cuenta> cuentas = usuarioService.findCuentasByUsuarioId(id);
        BigDecimal saldoTotal = usuarioService.getSaldoTotalUsuario(id);

        model.addAttribute("usuario", usuario);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("saldoTotal", saldoTotal);

        return "admin/usuario/usuario_detalle";
    }

    /**
     * Muestra el formulario para crear un nuevo usuario.
     *
     * @param model el modelo que se pasará a la vista
     * @return la vista del formulario de usuario
     */
    @GetMapping("/new")
    public String newUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuario/usuario_form";
    }

    /**
     * Agrega un nuevo usuario.
     *
     * @param usuario el usuario a agregar
     * @return la redirección a la lista de usuarios
     */
    @PostMapping("/new")
    public String addUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.addUsuario(usuario);
        return "redirect:/usuarios";
    }

    /**
     * Muestra el formulario para editar un usuario existente.
     *
     * @param id    el ID del usuario a editar
     * @param model el modelo que se pasará a la vista
     * @return la vista del formulario de usuario
     */
    @GetMapping("/{id}/edit")
    public String editUsuarioForm(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        return "admin/usuario/usuario_form";
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id      el ID del usuario a actualizar
     * @param usuario el usuario con la información actualizada
     * @return la redirección a la lista de usuarios
     */
    @PostMapping("/{id}")
    public String updateUsuario(@PathVariable("id") Long id, @ModelAttribute Usuario usuario) {
        usuario.setId(id);
        usuarioService.updateUsuario(usuario);
        return "redirect:/usuarios";
    }

    /**
     * Elimina un usuario específico.
     *
     * @param id el ID del usuario a eliminar
     * @return la redirección a la lista de usuarios
     */
    @GetMapping("/{id}/delete")
    public String deleteUsuario(@PathVariable("id") Long id) {
        usuarioService.deleteUsuario(id);
        return "redirect:/usuarios";
    }

    /**
     * Agrega una cuenta a un usuario específico.
     *
     * @param usuarioId el ID del usuario
     * @param cuenta    la cuenta a agregar
     * @return la redirección a los detalles del usuario
     */
    @PostMapping("/{usuarioId}/cuentas")
    public String addCuentaToUsuario(@PathVariable("usuarioId") Long usuarioId, @ModelAttribute Cuenta cuenta) {
        usuarioService.addCuentaToUsuario(usuarioId, cuenta);
        return "redirect:/usuarios/" + usuarioId;
    }

    /**
     * Elimina una cuenta de un usuario específico.
     *
     * @param usuarioId el ID del usuario
     * @param cuentaId  el ID de la cuenta a eliminar
     * @return la redirección a los detalles del usuario
     */
    @GetMapping("/{usuarioId}/cuentas/{cuentaId}/delete")
    public String removeCuentaFromUsuario(@PathVariable("usuarioId") Long usuarioId,
            @PathVariable("cuentaId") Long cuentaId) {
        usuarioService.removeCuentaFromUsuario(usuarioId, cuentaId);
        return "redirect:/usuarios/" + usuarioId;
    }
}
