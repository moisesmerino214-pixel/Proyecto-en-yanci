/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import arbol.ArbolBBusqueda;
import modelo.Empleado;

/**
 *
 * @author moise
 */
public interface EmpleadoInterfaz {
    boolean agregar(Empleado emp,String nombreUsuario, String contrasena) throws Exception;
    boolean modificar(Empleado emp, String nuevaContrasena) throws Exception;
    boolean eliminar(int id) throws Exception;
    ArbolBBusqueda<Empleado> listar() throws Exception;
    
}
