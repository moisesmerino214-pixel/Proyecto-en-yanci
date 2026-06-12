/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import arbol.ArbolAVL;
import modelo.Inventario;

/**
 *
 * @author moise
 */
public interface InventarioInterfaz {
    ArbolAVL listar() throws Exception;
    void registrar(Inventario inv) throws Exception;
    void modificar(Inventario inv) throws Exception;
    void eliminar(int id) throws Exception;
}