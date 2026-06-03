/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import arbol.ArbolBBusqueda;
import conexion.Conexion;
import interfaz.MasVendidosInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.ProductoMasVendido;

/**
 *
 * @author moise
 */
public class MasVendidosDAO implements MasVendidosInterface {
    
    private static final String SELECT_TOP_VENDIDOS = "SELECT p.id_producto, p.nombre_comercial, p.precio_venta, SUM(v.cantidad) as total_vendido " + "FROM productos p " + "JOIN ventas v ON p.nombre_comercial = v.producto " + "GROUP BY p.id_producto, p.nombre_comercial, p.precio_venta " + "ORDER BY total_vendido DESC";
    
    
    @Override
    public ArbolBBusqueda<ProductoMasVendido> listarMasVendidos() throws Exception {
        ArbolBBusqueda<ProductoMasVendido> arbol = new ArbolBBusqueda<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion();
            ps = conn.prepareStatement(SELECT_TOP_VENDIDOS);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProductoMasVendido p = new ProductoMasVendido();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre_comercial"));
                p.setPrecio(rs.getDouble("precio_venta"));
                p.setParesVendidos(rs.getInt("total_vendido"));
                arbol.insertar(p);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
        return arbol;
    }
}