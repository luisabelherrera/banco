package com.tecno.web_sec.models; // Paquete donde se encuentra la clase Usuario

import java.math.BigDecimal; // Importación para manejar cantidades monetarias
import java.util.ArrayList; // Importación para utilizar listas dinámicas
import java.util.List; // Importación para trabajar con listas

/**
 * Clase que representa un usuario en el sistema.
 * Un usuario tiene un identificador, nombre de usuario, contraseña,
 * rol y una lista de cuentas asociadas.
 */
public class Usuario {

    // Atributos de la clase
    private Long id; // Identificador único del usuario
    private String username; // Nombre de usuario
    private String password; // Contraseña del usuario
    private String role; // Rol del usuario (por ejemplo, admin, cliente)
    private List<Cuenta> cuentas; // Lista de cuentas asociadas al usuario

    // Constructor vacío
    public Usuario() {
        this.cuentas = new ArrayList<>(); // Inicializa la lista de cuentas vacía
    }

    /**
     * Constructor de la clase Usuario.
     * 
     * @param id       Identificador único del usuario
     * @param username Nombre de usuario
     * @param password Contraseña del usuario
     * @param role     Rol del usuario
     * @param cuentas  Lista de cuentas asociadas al usuario
     */
    public Usuario(Long id, String username, String password, String role, List<Cuenta> cuentas) {
        this.id = id; // Inicializa el id del usuario
        this.username = username; // Inicializa el nombre de usuario
        this.password = password; // Inicializa la contraseña
        this.role = role; // Inicializa el rol
        // Si las cuentas son nulas, se inicializa con una lista vacía
        this.cuentas = cuentas != null ? cuentas : new ArrayList<>();
    }

    // Métodos getter y setter para cada atributo

    /**
     * Obtiene el identificador único del usuario.
     * 
     * @return El id del usuario
     */
    public Long getId() {
        return id; // Retorna el id
    }

    /**
     * Establece el identificador único del usuario.
     * 
     * @param id El id del usuario
     */
    public void setId(Long id) {
        this.id = id; // Asigna el nuevo id
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return El nombre de usuario
     */
    public String getUsername() {
        return username; // Retorna el nombre de usuario
    }

    /**
     * Establece el nombre de usuario.
     * 
     * @param username El nombre a establecer
     */
    public void setUsername(String username) {
        this.username = username; // Asigna el nuevo nombre de usuario
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return La contraseña
     */
    public String getPassword() {
        return password; // Retorna la contraseña
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param password La contraseña a establecer
     */
    public void setPassword(String password) {
        this.password = password; // Asigna la nueva contraseña
    }

    /**
     * Obtiene el rol del usuario.
     * 
     * @return El rol del usuario
     */
    public String getRole() {
        return role; // Retorna el rol
    }

    /**
     * Establece el rol del usuario.
     * 
     * @param role El rol a establecer
     */
    public void setRole(String role) {
        this.role = role; // Asigna el nuevo rol
    }

    /**
     * Obtiene la lista de cuentas asociadas al usuario.
     * 
     * @return La lista de cuentas
     */
    public List<Cuenta> getCuentas() {
        return cuentas; // Retorna la lista de cuentas
    }

    /**
     * Establece la lista de cuentas asociadas al usuario.
     * 
     * @param cuentas La lista de cuentas a establecer
     */
    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas; // Asigna la nueva lista de cuentas
    }

    /**
     * Obtiene el saldo total de todas las cuentas asociadas al usuario.
     * 
     * @return El saldo total
     */
    public BigDecimal getSaldo() {
        return cuentas.stream() // Crea un flujo de cuentas
                .map(Cuenta::getSaldo) // Mapea cada cuenta a su saldo
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Suma todos los saldos
    }

    /**
     * Agrega una cuenta a la lista de cuentas del usuario.
     * 
     * @param cuenta La cuenta a agregar
     */
    public void addCuenta(Cuenta cuenta) {
        if (cuentas == null) {
            cuentas = new ArrayList<>(); // Inicializa la lista si es nula
        }
        cuentas.add(cuenta); // Agrega la cuenta
    }

    /**
     * Elimina una cuenta de la lista de cuentas del usuario.
     * 
     * @param cuenta La cuenta a eliminar
     */
    public void removeCuenta(Cuenta cuenta) {
        if (cuentas != null) {
            cuentas.remove(cuenta); // Elimina la cuenta si la lista no es nula
        }
    }

    @Override
    public String toString() {
        // Retorna una representación en forma de cadena del usuario
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", saldo=" + getSaldo() +
                '}';
    }
}
