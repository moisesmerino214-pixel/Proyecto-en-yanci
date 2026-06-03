/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import arbol.ArbolBBusqueda;
import modeloVendedor.DetalleVenta;
import modeloVendedor.Ventas;

/**
 *
 * @author moise
 */
public interface GestionVentasInterfaz {
    ArbolBBusqueda<Ventas> listarVentas() throws Exception;
    ArbolBBusqueda<Ventas> buscarPorCliente(String criterio) throws Exception;
    ArbolBBusqueda<Ventas> buscarPorVendedor(String criterio) throws Exception;
    ArbolBBusqueda<Ventas> buscarPorFecha(String fecha) throws Exception;
    ArbolBBusqueda<DetalleVenta> listarDetalle(int idVenta) throws Exception;
    void anularVenta(int idVenta) throws Exception;
}

