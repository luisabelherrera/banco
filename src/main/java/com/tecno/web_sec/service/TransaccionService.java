package com.tecno.web_sec.service;

import java.util.List;

import com.tecno.web_sec.models.Transaccion;

/**
 * Interfaz que define los métodos para gestionar las transacciones financieras.
 */
public interface TransaccionService {

    /**
     * Obtiene la lista de todas las transacciones.
     * 
     * @return una lista de objetos {@link Transaccion}
     */
    List<Transaccion> findAll();

    /**
     * Busca una transacción por su ID.
     * 
     * @param id el ID de la transacción a buscar
     * @return el objeto {@link Transaccion} correspondiente al ID proporcionado
     */
    Transaccion findById(Long id);

    /**
     * Agrega una nueva transacción.
     * 
     * @param transaccion el objeto {@link Transaccion} a agregar
     */
    void addTransaccion(Transaccion transaccion);

    /**
     * Busca las transacciones asociadas a una cuenta específica.
     * 
     * @param cuentaId el ID de la cuenta para la que se buscan transacciones
     * @return una lista de objetos {@link Transaccion} asociadas a la cuenta
     */
    List<Transaccion> findByCuentaId(Long cuentaId);

    /**
     * Elimina una transacción por su ID.
     * 
     * @param id el ID de la transacción a eliminar
     */
    void deleteTransaccion(Long id);
}
