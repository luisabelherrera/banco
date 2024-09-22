package com.tecno.web_sec.service.impl; // Paquete donde se encuentra la clase

import java.math.BigDecimal; // Importación para manejar valores monetarios
import java.util.ArrayList; // Importación para usar listas dinámicas
import java.util.List; // Importación para la interfaz de listas

import org.springframework.beans.factory.annotation.Autowired; // Importación para la inyección de dependencias
import org.springframework.security.crypto.password.PasswordEncoder; // Importación para codificación de contraseñas
import org.springframework.stereotype.Service; // Importación para definir un servicio de Spring

import com.tecno.web_sec.models.Cuenta; // Importación del modelo Cuenta
import com.tecno.web_sec.models.Usuario; // Importación del modelo Usuario
import com.tecno.web_sec.service.CuentaService; // Importación de la interfaz CuentaService
import com.tecno.web_sec.service.UsuarioService; // Importación de la interfaz UsuarioService

import jakarta.annotation.PostConstruct; // Importación para métodos que deben ejecutarse después de la construcción

/**
 * Implementación del servicio de cuentas que maneja la lógica de negocio
 * relacionada con las cuentas de usuario.
 */
@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private UsuarioService usuarioService; // Servicio de usuario para gestionar cuentas de usuario

    @Autowired
    private PasswordEncoder passwordEncoder; // Codificador de contraseñas para codificar las contraseñas de los
                                             // usuarios

    private List<Cuenta> cuentas = new ArrayList<>(); // Lista para almacenar cuentas
    private Long idCounter = 1L; // Contador para asignar IDs únicos a las cuentas
    private List<Usuario> usuarios = new ArrayList<>(); // Lista para almacenar usuarios

    /**
     * Método que se ejecuta después de la construcción del objeto para inicializar
     * datos de prueba.
     */
    @PostConstruct
    public void init() {
        // Creación de usuarios de prueba
        Usuario usuario1 = new Usuario(idCounter++, "usuario1", passwordEncoder.encode("123"), "USER",
                new ArrayList<>());
        Usuario usuario2 = new Usuario(idCounter++, "usuario2", passwordEncoder.encode("123"), "USER",
                new ArrayList<>());
        Usuario usuario3 = new Usuario(idCounter++, "admin", passwordEncoder.encode("123"), "ADMIN", new ArrayList<>());
        Usuario usuario4 = new Usuario(idCounter++, "usuario4", passwordEncoder.encode("123"), "USER",
                new ArrayList<>());
        Usuario usuario5 = new Usuario(idCounter++, "usuario5", passwordEncoder.encode("456"), "USER",
                new ArrayList<>());
        Usuario usuario6 = new Usuario(idCounter++, "usuario6", passwordEncoder.encode("456"), "USER",
                new ArrayList<>());

        // Agregar usuarios a la lista
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(usuario3);
        usuarios.add(usuario4);
        usuarios.add(usuario5);
        usuarios.add(usuario6);

        // Creación y asignación de cuentas a los usuarios
        Cuenta cuentaA = new Cuenta(1L, "Cuenta A", new BigDecimal("1000.00"), usuario1);
        usuario1.getCuentas().add(cuentaA);
        cuentas.add(cuentaA);

        Cuenta cuentaB = new Cuenta(2L, "Cuenta B", new BigDecimal("1500.00"), usuario2);
        usuario2.getCuentas().add(cuentaB);
        cuentas.add(cuentaB);

        Cuenta cuentaC = new Cuenta(3L, "Cuenta C", new BigDecimal("2000.00"), usuario3);
        usuario3.getCuentas().add(cuentaC);
        cuentas.add(cuentaC);

        Cuenta cuentaD = new Cuenta(4L, "Cuenta D", new BigDecimal("2500.00"), usuario4);
        usuario4.getCuentas().add(cuentaD);
        cuentas.add(cuentaD);

        Cuenta cuentaE = new Cuenta(5L, "Cuenta E", new BigDecimal("3000.00"), usuario5);
        usuario5.getCuentas().add(cuentaE);
        cuentas.add(cuentaE);

        Cuenta cuentaF = new Cuenta(6L, "Cuenta F", new BigDecimal("3500.00"), usuario6);
        usuario6.getCuentas().add(cuentaF);
        cuentas.add(cuentaF);
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentas; // Devuelve todas las cuentas
    }

    @Override
    public Cuenta findById(Long id) {
        return cuentas.stream() // Busca la cuenta por ID
                .filter(cuenta -> cuenta.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Cuenta not found")); // Lanza excepción si no se encuentra
    }

    @Override
    public void addCuenta(Cuenta cuenta) {
        cuentas.add(cuenta); // Agrega una nueva cuenta
        Usuario usuario = cuenta.getUsuario(); // Obtiene el usuario asociado
        if (usuario != null) {
            usuarioService.addCuentaToUsuario(usuario.getId(), cuenta); // Agrega la cuenta al usuario
        }
    }

    @Override
    public void transferir(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto) {
        Cuenta cuentaOrigen = findById(cuentaOrigenId); // Busca la cuenta de origen
        Cuenta cuentaDestino = findById(cuentaDestinoId); // Busca la cuenta de destino

        if (cuentaOrigen.getSaldo().compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente en la cuenta de origen"); // Verifica saldo
        }

        cuentaOrigen.retirar(monto); // Retira el monto de la cuenta de origen
        cuentaDestino.depositar(monto); // Deposita el monto en la cuenta de destino
    }

    @Override
    public void updateCuenta(Cuenta updatedCuenta) {
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta cuenta = cuentas.get(i);
            if (cuenta.getId().equals(updatedCuenta.getId())) {
                cuentas.set(i, updatedCuenta); // Actualiza la cuenta
                return;
            }
        }
        throw new RuntimeException("Cuenta not found"); // Lanza excepción si no se encuentra
    }

    @Override
    public void deleteCuenta(Long id) {
        Cuenta cuenta = findById(id); // Busca la cuenta por ID
        cuentas.remove(cuenta); // Elimina la cuenta de la lista
        Usuario usuario = cuenta.getUsuario(); // Obtiene el usuario asociado
        if (usuario != null) {
            usuarioService.removeCuentaFromUsuario(usuario.getId(), cuenta.getId()); // Elimina la cuenta del usuario
        }
    }

    @Override
    public void depositar(Long id, BigDecimal monto) {
        Cuenta cuenta = findById(id); // Busca la cuenta por ID
        cuenta.depositar(monto); // Deposita el monto en la cuenta
    }

    @Override
    public void retirar(Long id, BigDecimal monto) {
        Cuenta cuenta = findById(id); // Busca la cuenta por ID
        cuenta.retirar(monto); // Retira el monto de la cuenta
    }
}
