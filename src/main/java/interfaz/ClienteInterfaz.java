/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import arbol.ArbolBBusqueda;
import modelo.Cliente;

/**
 *
 * @author moise
 */
public interface ClienteInterfaz {
    ArbolBBusqueda<Cliente> listar() throws Exception;
    void agregar(Cliente c) throws Exception;
    void modificar(Cliente c) throws Exception;
    void eliminar(int id) throws Exception;
}
