package com.tecno.web_sec.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tecno.web_sec.models.Transaccion;
import com.tecno.web_sec.service.TransaccionService;

/**
 * Implementación del servicio de transacciones que gestiona la lógica de
 * negocio
 * relacionada con las transacciones financieras.
 */
@Service
public class TransaccionServiceImpl implements TransaccionService {

    // Lista que almacena las transacciones en memoria
    private List<Transaccion> transacciones = new ArrayList<>(Arrays.asList());
    // Contador para asignar IDs únicos a las transacciones
    private Long idCounter = 11L;

    @Override
    public List<Transaccion> findAll() {
        // Retorna la lista de todas las transacciones
        return transacciones;
    }

    @Override
    public Transaccion findById(Long id) {
        // Busca una transacción por su ID y lanza una excepción si no se encuentra
        return transacciones.stream()
                .filter(transaccion -> transaccion.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }

    @Override
    public List<Transaccion> findByCuentaId(Long cuentaId) {
        // Busca las transacciones relacionadas con una cuenta específica
        return transacciones.stream()
                .filter(transaccion -> (transaccion.getCuentaOrigen() != null
                        && transaccion.getCuentaOrigen().getId().equals(cuentaId)) ||
                        (transaccion.getCuentaDestino() != null
                                && transaccion.getCuentaDestino().getId().equals(cuentaId)))
                .collect(Collectors.toList());
    }

    @Override
    public void addTransaccion(Transaccion transaccion) {
        // Agrega una nueva transacción, asegurándose de que las cuentas de origen y
        // destino estén presentes
        if (transaccion.getCuentaOrigen() == null || transaccion.getCuentaDestino() == null) {
            throw new RuntimeException("Las cuentas de origen y destino deben estar presentes");
        }
        transaccion.setId(idCounter++); // Asigna un ID único a la transacción
        transacciones.add(transaccion); // Agrega la transacción a la lista
    }

    @Override
    public void deleteTransaccion(Long id) {
        // Elimina una transacción por su ID
        transacciones.removeIf(transaccion -> transaccion.getId().equals(id));
    }
}
