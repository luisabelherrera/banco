package com.tecno.web_sec.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tecno.web_sec.models.Cuenta;
import com.tecno.web_sec.models.Usuario;
import com.tecno.web_sec.service.UsuarioService;

import jakarta.annotation.PostConstruct;

/**
 * Implementación del servicio de usuarios que gestiona la lógica de negocio
 * relacionada con los usuarios y sus cuentas. También implementa
 * UserDetailsService
 * para la integración con Spring Security.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Lista que almacena los usuarios en memoria
    private List<Usuario> usuarios = new ArrayList<>();
    // Contador para asignar IDs únicos a los usuarios
    private Long idCounter = 1L;

    /**
     * Método que inicializa usuarios y cuentas predeterminados al iniciar el
     * servicio.
     * Este método se ejecuta automáticamente después de la construcción del bean.
     */
    @PostConstruct
    public void init() {
        // Creación de usuarios predeterminados
        Usuario usuario1 = new Usuario(idCounter++, "usuario1", passwordEncoder.encode("123"), "USER",
                new ArrayList<>());
        Usuario usuario2 = new Usuario(idCounter++, "usuario2", passwordEncoder.encode("123"), "USER",
                new ArrayList<>());
        Usuario usuario3 = new Usuario(idCounter++, "admin", passwordEncoder.encode("123"), "ADMIN", new ArrayList<>());
        Usuario usuario4 = new Usuario(idCounter++, "usuario4", passwordEncoder.encode("123"), "USER",
                new ArrayList<>());
        Usuario usuario5 = new Usuario(idCounter++, "usuario5", passwordEncoder.encode("123"), "USER",
                new ArrayList<>());
        Usuario usuario6 = new Usuario(idCounter++, "usuario6", passwordEncoder.encode("123"), "USER",
                new ArrayList<>());

        // Agregar usuarios a la lista
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(usuario3);
        usuarios.add(usuario4);
        usuarios.add(usuario5);
        usuarios.add(usuario6);

        // Creación de cuentas y asociación con usuarios
        Cuenta cuentaA = new Cuenta(1L, "Cuenta A", new BigDecimal("1000.00"), usuario1);
        Cuenta cuentaB = new Cuenta(2L, "Cuenta B", new BigDecimal("1500.00"), usuario2);
        Cuenta cuentaC = new Cuenta(3L, "admin", new BigDecimal("2000.00"), usuario3);
        Cuenta cuentaD = new Cuenta(4L, "Cuenta D", new BigDecimal("2500.00"), usuario4);
        Cuenta cuentaE = new Cuenta(5L, "Cuenta E", new BigDecimal("3000.00"), usuario5);
        Cuenta cuentaF = new Cuenta(6L, "Cuenta F", new BigDecimal("3500.00"), usuario6);

        // Asignar cuentas a los usuarios
        usuario1.getCuentas().add(cuentaA);
        usuario2.getCuentas().add(cuentaB);
        usuario3.getCuentas().add(cuentaC);
        usuario4.getCuentas().add(cuentaD);
        usuario5.getCuentas().add(cuentaE);
        usuario6.getCuentas().add(cuentaF);
    }

    @Override
    public List<Usuario> findAll() {
        // Retorna la lista de todos los usuarios
        return usuarios;
    }

    @Override
    public Usuario findById(Long id) {
        // Busca un usuario por su ID y lanza una excepción si no se encuentra
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public List<Cuenta> findCuentasByUsuarioId(Long usuarioId) {
        // Busca las cuentas asociadas a un usuario por su ID
        Usuario usuario = findById(usuarioId);
        return usuario.getCuentas();
    }

    @Override
    public void addUsuario(Usuario usuario) {
        // Agrega un nuevo usuario, verificando que el nombre de usuario no esté en uso
        if (findByUsername(usuario.getUsername()) != null) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        usuario.setId(idCounter++);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Codifica la contraseña
        usuario.setRole((usuario.getRole() == null) ? "USER" : usuario.getRole());
        usuarios.add(usuario);
    }

    @Override
    public void updateUsuario(Usuario updatedUsuario) {
        // Actualiza la información de un usuario existente
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            if (usuario.getId().equals(updatedUsuario.getId())) {
                usuarios.set(i, updatedUsuario);
                return;
            }
        }
    }

    @Override
    public void deleteUsuario(Long id) {
        // Elimina un usuario por su ID
        usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }

    @Override
    public void addCuentaToUsuario(Long usuarioId, Cuenta cuenta) {
        // Agrega una nueva cuenta a un usuario
        Usuario usuario = findById(usuarioId);
        cuenta.setUsuario(usuario);
        usuario.addCuenta(cuenta);
    }

    @Override
    public void removeCuentaFromUsuario(Long usuarioId, Long cuentaId) {
        // Elimina una cuenta de un usuario por su ID
        Usuario usuario = findById(usuarioId);
        usuario.getCuentas().removeIf(cuenta -> cuenta.getId().equals(cuentaId));
    }

    @Override
    public BigDecimal getSaldoTotalUsuario(Long usuarioId) {
        // Retorna el saldo total de un usuario
        Usuario usuario = findById(usuarioId);
        return usuario.getSaldo();
    }

    @Override
    public Usuario findByUsername(String username) {
        // Busca un usuario por su nombre de usuario
        return usuarios.stream()
                .filter(usuario -> usuario.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con el nombre de usuario: " + username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Carga los detalles de usuario para la autenticación
        Usuario usuario = findByUsername(username);
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRole())
                .build();
    }
}
