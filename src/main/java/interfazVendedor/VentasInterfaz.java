/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfazVendedor;

import arbol.ArbolBBusqueda;
import modeloVendedor.DetalleVenta;
import modeloVendedor.Ventas;

/**
 *
 * @author moise
 */
public interface VentasInterfaz {
    int registrarVenta(Ventas v) throws Exception;
    void registrarDetalle(DetalleVenta d) throws Exception;
    ArbolBBusqueda<DetalleVenta> listarDetalle(int idVenta) throws Exception;
    void cargarClientes(javax.swing.JComboBox<String> combo) throws Exception;
    void cargarVendedores(javax.swing.JComboBox<String> combo) throws Exception;
    void cargarProductos(javax.swing.JComboBox<String> combo) throws Exception;
    void cargarColoresPorProducto(javax.swing.JComboBox<String> combo, String producto) throws Exception;
    void cargarTallas(javax.swing.JComboBox<String> combo, String producto, String color) throws Exception;
    double obtenerPrecio(String producto) throws Exception;
    int obtenerIdInventario(String producto, String color, String talla) throws Exception;
}