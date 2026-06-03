/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import arbol.ArbolBBusqueda;
import modelo.Producto;

/**
 *
 * @author moise
 */
public interface ProductoInterfaz {
 
    ArbolBBusqueda<Producto> listar() throws Exception;
    void agregar(Producto p) throws Exception;
    void modificar(Producto p) throws Exception;
    void eliminar(int id) throws Exception;
    Producto buscar(int id) throws Exception;
    void cargarMarcas(javax.swing.JComboBox<String> combo) throws Exception;
    void cargarColores(javax.swing.JComboBox<String> combo) throws Exception;
}
 