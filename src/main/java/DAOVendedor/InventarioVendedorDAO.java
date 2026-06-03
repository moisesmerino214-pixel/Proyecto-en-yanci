/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOVendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author moise
 */
public class InventarioVendedorDAO {
   private static final String SELECT_INVENTARIO = "SELECT i.id_inventario, p.nombre_comercial, i.talla, i.color, i.stock, p.precio_venta " + "FROM inventario i JOIN productos p ON i.id_producto = p.id_producto " + "ORDER BY p.nombre_comercial ASC";
   private static final String BUSCAR_INVENTARIO = "SELECT i.id_inventario, p.nombre_comercial, i.talla, i.color, i.stock, p.precio_venta " + "FROM inventario i JOIN productos p ON i.id_producto = p.id_producto " + "WHERE LOWER(p.nombre_comercial) LIKE LOWER(?) " + "ORDER BY p.nombre_comercial ASC";
    
   public void cargarInventario(javax.swing.JTable tabla) throws Exception {
        Connection conn = conexion.Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_INVENTARIO);
        ResultSet rs = ps.executeQuery();
        llenarTabla(tabla, rs);
        rs.close(); ps.close(); conn.close();
    }
   public void buscarInventario(javax.swing.JTable tabla, String criterio) throws Exception {
        Connection conn = conexion.Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(BUSCAR_INVENTARIO);
        ps.setString(1, "%" + criterio + "%");
        ResultSet rs = ps.executeQuery();
        llenarTabla(tabla, rs);
        rs.close(); ps.close(); conn.close();
    }
   
   private void llenarTabla(javax.swing.JTable tabla, ResultSet rs) throws Exception {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getInt("id_inventario"),
                rs.getString("nombre_comercial"),
                rs.getDouble("talla"),
                rs.getString("color"),
                rs.getInt("stock"),
                String.format("$%.2f", rs.getDouble("precio_venta"))
            });
        }
    }
}
