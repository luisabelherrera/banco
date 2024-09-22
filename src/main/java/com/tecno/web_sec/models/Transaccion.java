package com.tecno.web_sec.models; // Paquete donde se encuentra la clase Transaccion

import java.math.BigDecimal; // Importación para manejar cantidades monetarias
import java.time.LocalDateTime; // Importación para manejar fechas y horas

/**
 * Clase que representa una transacción entre cuentas.
 * Una transacción incluye información sobre el monto, la fecha,
 * y las cuentas de origen y destino involucradas.
 */
public class Transaccion {

    // Atributos de la clase
    private Long id; // Identificador único de la transacción
    private BigDecimal monto; // Monto de dinero involucrado en la transacción
    private LocalDateTime fecha; // Fecha y hora en que se realizó la transacción
    private Cuenta cuentaOrigen; // Cuenta desde donde se origina el monto
    private Cuenta cuentaDestino; // Cuenta a la que se envía el monto

    // Constructor vacío
    public Transaccion() {
    }

    /**
     * Constructor de la clase Transaccion.
     * 
     * @param id            Identificador único de la transacción
     * @param monto         Monto de la transacción
     * @param fecha         Fecha y hora de la transacción
     * @param cuentaOrigen  Cuenta desde donde se origina el monto
     * @param cuentaDestino Cuenta a la que se envía el monto
     */
    public Transaccion(Long id, BigDecimal monto, LocalDateTime fecha, Cuenta cuentaOrigen, Cuenta cuentaDestino) {
        this.id = id; // Inicializa el id de la transacción
        this.monto = monto; // Inicializa el monto de la transacción
        this.fecha = fecha; // Inicializa la fecha de la transacción
        this.cuentaOrigen = cuentaOrigen; // Inicializa la cuenta de origen
        this.cuentaDestino = cuentaDestino; // Inicializa la cuenta de destino
    }

    // Métodos getter y setter para cada atributo

    /**
     * Obtiene el identificador único de la transacción.
     * 
     * @return El id de la transacción
     */
    public Long getId() {
        return id; // Retorna el id
    }

    /**
     * Establece el identificador único de la transacción.
     * 
     * @param id El id de la transacción
     */
    public void setId(Long id) {
        this.id = id; // Asigna el nuevo id
    }

    /**
     * Obtiene el monto de la transacción.
     * 
     * @return El monto de la transacción
     */
    public BigDecimal getMonto() {
        return monto; // Retorna el monto
    }

    /**
     * Establece el monto de la transacción.
     * 
     * @param monto El monto a establecer
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto; // Asigna el nuevo monto
    }

    /**
     * Obtiene la fecha y hora de la transacción.
     * 
     * @return La fecha de la transacción
     */
    public LocalDateTime getFecha() {
        return fecha; // Retorna la fecha
    }

    /**
     * Establece la fecha y hora de la transacción.
     * 
     * @param fecha La fecha a establecer
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha; // Asigna la nueva fecha
    }

    /**
     * Obtiene la cuenta de origen de la transacción.
     * 
     * @return La cuenta de origen
     */
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen; // Retorna la cuenta de origen
    }

    /**
     * Establece la cuenta de origen de la transacción.
     * 
     * @param cuentaOrigen La cuenta a establecer como origen
     */
    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen; // Asigna la nueva cuenta de origen
    }

    /**
     * Obtiene la cuenta de destino de la transacción.
     * 
     * @return La cuenta de destino
     */
    public Cuenta getCuentaDestino() {
        return cuentaDestino; // Retorna la cuenta de destino
    }

    /**
     * Establece la cuenta de destino de la transacción.
     * 
     * @param cuentaDestino La cuenta a establecer como destino
     */
    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino; // Asigna la nueva cuenta de destino
    }
}
