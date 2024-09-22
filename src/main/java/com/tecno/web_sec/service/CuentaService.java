package com.tecno.web_sec.service;

import java.math.BigDecimal;
import java.util.List;

import com.tecno.web_sec.models.Cuenta;

/**
 * Interfaz que define los métodos para gestionar las cuentas financieras.
 */
public interface CuentaService {

    /**
     * Obtiene la lista de todas las cuentas.
     * 
     * @return una lista de objetos {@link Cuenta}
     */
    List<Cuenta> findAll();

    /**
     * Busca una cuenta por su ID.
     * 
     * @param id el ID de la cuenta a buscar
     * @return el objeto {@link Cuenta} correspondiente al ID proporcionado
     */
    Cuenta findById(Long id);

    /**
     * Agrega una nueva cuenta.
     * 
     * @param cuenta el objeto {@link Cuenta} a agregar
     */
    void addCuenta(Cuenta cuenta);

    /**
     * Actualiza una cuenta existente.
     * 
     * @param updatedCuenta el objeto {@link Cuenta} con los datos actualizados
     */
    void updateCuenta(Cuenta updatedCuenta);

    /**
     * Elimina una cuenta por su ID.
     * 
     * @param id el ID de la cuenta a eliminar
     */
    void deleteCuenta(Long id);

    /**
     * Deposita un monto en la cuenta especificada.
     * 
     * @param id    el ID de la cuenta donde se realizará el depósito
     * @param monto el monto a depositar
     */
    void depositar(Long id, BigDecimal monto);

    /**
     * Retira un monto de la cuenta especificada.
     * 
     * @param id    el ID de la cuenta de la cual se retirará el monto
     * @param monto el monto a retirar
     */
    void retirar(Long id, BigDecimal monto);

    /**
     * Transfiere un monto de una cuenta a otra.
     * 
     * @param cuentaOrigenId  el ID de la cuenta de origen
     * @param cuentaDestinoId el ID de la cuenta de destino
     * @param monto           el monto a transferir
     */
    void transferir(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto);
}
