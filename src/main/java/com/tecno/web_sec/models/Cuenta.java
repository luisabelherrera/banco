package com.tecno.web_sec.models; // Paquete donde se encuentra la clase Cuenta

import java.math.BigDecimal; // Importación para manejar cantidades monetarias
import java.util.ArrayList; // Importación para utilizar listas dinámicas
import java.util.List; // Importación para trabajar con listas

/**
 * Clase que representa una cuenta bancaria.
 * Cada cuenta tiene un identificador, un nombre, un saldo,
 * un usuario asociado y una lista de transacciones.
 */
public class Cuenta {

    // Atributos de la clase
    private Long id; // Identificador único de la cuenta
    private String nombre; // Nombre de la cuenta
    private BigDecimal saldo = BigDecimal.ZERO; // Saldo de la cuenta, inicializado en cero
    private Usuario usuario; // Usuario asociado a la cuenta
    private List<Transaccion> transacciones = new ArrayList<>(); // Lista de transacciones asociadas a la cuenta

    /**
     * Constructor de la clase Cuenta.
     * 
     * @param id      Identificador único de la cuenta
     * @param nombre  Nombre de la cuenta
     * @param saldo   Saldo inicial de la cuenta
     * @param usuario Usuario asociado a la cuenta
     */
    public Cuenta(Long id, String nombre, BigDecimal saldo, Usuario usuario) {
        this.id = id; // Inicializa el id de la cuenta
        this.nombre = nombre; // Inicializa el nombre de la cuenta
        this.saldo = saldo; // Inicializa el saldo de la cuenta
        this.usuario = usuario; // Inicializa el usuario asociado a la cuenta
    }

    // Constructor vacío
    public Cuenta() {
    }

    // Métodos getter y setter para cada atributo

    /**
     * Obtiene el identificador único de la cuenta.
     * 
     * @return El id de la cuenta
     */
    public Long getId() {
        return id; // Retorna el id
    }

    /**
     * Establece el identificador único de la cuenta.
     * 
     * @param id El id de la cuenta
     */
    public void setId(Long id) {
        this.id = id; // Asigna el nuevo id
    }

    /**
     * Obtiene el nombre de la cuenta.
     * 
     * @return El nombre de la cuenta
     */
    public String getNombre() {
        return nombre; // Retorna el nombre de la cuenta
    }

    /**
     * Establece el nombre de la cuenta.
     * 
     * @param nombre El nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre; // Asigna el nuevo nombre de la cuenta
    }

    /**
     * Obtiene el saldo de la cuenta.
     * 
     * @return El saldo de la cuenta
     */
    public BigDecimal getSaldo() {
        return saldo; // Retorna el saldo
    }

    /**
     * Establece el saldo de la cuenta.
     * 
     * @param saldo El saldo a establecer
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo; // Asigna el nuevo saldo
    }

    /**
     * Obtiene el usuario asociado a la cuenta.
     * 
     * @return El usuario asociado
     */
    public Usuario getUsuario() {
        return usuario; // Retorna el usuario asociado
    }

    /**
     * Establece el usuario asociado a la cuenta.
     * 
     * @param usuario El usuario a establecer
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario; // Asigna el nuevo usuario asociado
    }

    /**
     * Obtiene la lista de transacciones asociadas a la cuenta.
     * 
     * @return La lista de transacciones
     */
    public List<Transaccion> getTransacciones() {
        return transacciones; // Retorna la lista de transacciones
    }

    /**
     * Establece la lista de transacciones asociadas a la cuenta.
     * 
     * @param transacciones La lista de transacciones a establecer
     */
    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones; // Asigna la nueva lista de transacciones
    }

    /**
     * Realiza un depósito en la cuenta.
     * 
     * @param monto Monto a depositar
     * @throws IllegalArgumentException Si el monto es nulo o menor o igual a cero
     */
    public void depositar(BigDecimal monto) {
        if (monto != null && monto.compareTo(BigDecimal.ZERO) > 0) {
            saldo = saldo.add(monto); // Suma el monto al saldo
        } else {
            throw new IllegalArgumentException("El monto debe ser mayor que cero."); // Lanza excepción si el monto no
                                                                                     // es válido
        }
    }

    /**
     * Realiza un retiro de la cuenta.
     * 
     * @param monto Monto a retirar
     * @throws IllegalArgumentException Si el monto es nulo o menor o igual a cero,
     *                                  o si hay saldo insuficiente
     */
    public void retirar(BigDecimal monto) {
        if (monto != null && monto.compareTo(BigDecimal.ZERO) > 0) {
            if (saldo.compareTo(monto) >= 0) {
                saldo = saldo.subtract(monto); // Resta el monto del saldo
            } else {
                throw new IllegalArgumentException("Saldo insuficiente."); // Lanza excepción si hay saldo insuficiente
            }
        } else {
            throw new IllegalArgumentException("El monto debe ser mayor que cero."); // Lanza excepción si el monto no
                                                                                     // es válido
        }
    }

    /**
     * Agrega una transacción a la lista de transacciones de la cuenta.
     * 
     * @param transaccion La transacción a agregar
     */
    public void agregarTransaccion(Transaccion transaccion) {
        if (transaccion != null) {
            transacciones.add(transaccion); // Agrega la transacción a la lista
            transaccion.setCuentaOrigen(this); // Establece la cuenta de origen en la transacción
        }
    }

    /**
     * Elimina una transacción de la lista de transacciones de la cuenta.
     * 
     * @param transaccion La transacción a eliminar
     */
    public void eliminarTransaccion(Transaccion transaccion) {
        if (transaccion != null) {
            transacciones.remove(transaccion); // Elimina la transacción de la lista
            transaccion.setCuentaOrigen(null); // Limpia la cuenta de origen en la transacción
        }
    }
}
