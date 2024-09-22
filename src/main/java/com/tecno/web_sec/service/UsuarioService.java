package com.tecno.web_sec.service;

import java.math.BigDecimal;
import java.util.List;

import com.tecno.web_sec.models.Cuenta;
import com.tecno.web_sec.models.Usuario;

/**
 * Interfaz que define los métodos para gestionar usuarios en el sistema.
 */
public interface UsuarioService {

    /**
     * Obtiene la lista de todos los usuarios.
     * 
     * @return una lista de objetos {@link Usuario}
     */
    List<Usuario> findAll();

    /**
     * Busca un usuario por su ID.
     * 
     * @param id el ID del usuario a buscar
     * @return el objeto {@link Usuario} correspondiente al ID proporcionado
     */
    Usuario findById(Long id);

    /**
     * Busca las cuentas asociadas a un usuario específico.
     * 
     * @param usuarioId el ID del usuario para el que se buscan cuentas
     * @return una lista de objetos {@link Cuenta} asociadas al usuario
     */
    List<Cuenta> findCuentasByUsuarioId(Long usuarioId);

    /**
     * Agrega un nuevo usuario al sistema.
     * 
     * @param usuario el objeto {@link Usuario} a agregar
     */
    void addUsuario(Usuario usuario);

    /**
     * Actualiza la información de un usuario existente.
     * 
     * @param updatedUsuario el objeto {@link Usuario} con la información
     *                       actualizada
     */
    void updateUsuario(Usuario updatedUsuario);

    /**
     * Elimina un usuario por su ID.
     * 
     * @param id el ID del usuario a eliminar
     */
    void deleteUsuario(Long id);

    /**
     * Asocia una cuenta a un usuario.
     * 
     * @param usuarioId el ID del usuario al que se le asociará la cuenta
     * @param cuenta    el objeto {@link Cuenta} a asociar
     */
    void addCuentaToUsuario(Long usuarioId, Cuenta cuenta);

    /**
     * Elimina una cuenta asociada a un usuario.
     * 
     * @param usuarioId el ID del usuario del que se eliminará la cuenta
     * @param cuentaId  el ID de la cuenta a eliminar
     */
    void removeCuentaFromUsuario(Long usuarioId, Long cuentaId);

    /**
     * Obtiene el saldo total de un usuario.
     * 
     * @param usuarioId el ID del usuario cuyo saldo se desea obtener
     * @return el saldo total del usuario como {@link BigDecimal}
     */
    BigDecimal getSaldoTotalUsuario(Long usuarioId);

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username el nombre de usuario a buscar
     * @return el objeto {@link Usuario} correspondiente al nombre de usuario
     *         proporcionado
     */
    Usuario findByUsername(String username);
}
