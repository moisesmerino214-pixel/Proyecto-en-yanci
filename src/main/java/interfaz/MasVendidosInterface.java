/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import arbol.ArbolBBusqueda;
import modelo.ProductoMasVendido;

/**
 *
 * @author moise
 */
public interface MasVendidosInterface {
    public ArbolBBusqueda<ProductoMasVendido> listarMasVendidos() throws Exception;
}
