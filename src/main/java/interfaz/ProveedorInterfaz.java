/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import arbol.ArbolBBusqueda;
import modelo.Proveedor;

/**
 *
 * @author moise
 */
public interface ProveedorInterfaz {
    public boolean agregar(Proveedor prov) throws Exception;
    public boolean modificar(Proveedor prov) throws Exception;
    public boolean eliminar(int id) throws Exception;
    public ArbolBBusqueda<Proveedor> listar() throws Exception; 
}
