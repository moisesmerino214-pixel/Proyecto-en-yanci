/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license

 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

 */

package DAO;



import arbol.ArbolBBusqueda;

import conexion.Conexion;

import modelo.Inventario;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import javax.swing.JComboBox;

/**

 *

 * @author moise

 */

public class InventarioDAO {

    private static final String SELECT_ALL = "SELECT i.*, p.nombre_comercial, p.precio_venta FROM inventario i JOIN productos p ON i.id_producto = p.id_producto";
    private static final String INSERT = "INSERT INTO inventario (id_producto, color, talla, stock) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE inventario SET id_producto=?, color=?, talla=?, stock=? WHERE id_inventario=?";
    private static final String DELETE = "DELETE FROM inventario WHERE id_inventario=?";
    private static final String SELECT_PRODUCTOS = "SELECT id_producto, nombre_comercial FROM productos";
    private static final String SELECT_BY_ID  = "SELECT i.*, p.nombre_comercial, p.precio_venta FROM inventario i " + "JOIN productos p ON i.id_producto = p.id_producto WHERE id_inventario=?";
    private static final String UPDATE_PRECIO = "UPDATE productos SET precio_venta=? WHERE id_producto=?";
    private static final String DELETE_DETALLES = "DELETE FROM detalleventas WHERE id_inventario=?";

    public ArbolBBusqueda<Inventario> listar() throws Exception {
        ArbolBBusqueda<Inventario> arbol = new ArbolBBusqueda<>();
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inventario inv = new Inventario();
                inv.setIdInventario(rs.getInt("id_inventario"));
                inv.setNombreProducto(rs.getString("nombre_comercial"));
                inv.setColor(rs.getString("color"));
                inv.setTalla(rs.getString("talla"));
                inv.setStock(rs.getInt("stock"));
                inv.setPrecio(rs.getDouble("precio_venta"));
                arbol.insertar(inv);
            }
        }
        return arbol;
    }

    public void registrar(Inventario inv) throws Exception {
        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(INSERT)) {
                    ps.setInt(1, inv.getIdProducto());
                    ps.setString(2, inv.getColor());
                    ps.setDouble(3, Double.parseDouble(inv.getTalla()));
                    ps.setInt(4, inv.getStock());
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(UPDATE_PRECIO)) {
                    ps.setDouble(1, inv.getPrecio());
                    ps.setInt(2, inv.getIdProducto());
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void modificar(Inventario inv) throws Exception {
        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
                    ps.setInt(1, inv.getIdProducto());
                    ps.setString(2, inv.getColor());
                    ps.setDouble(3, Double.parseDouble(inv.getTalla()));
                    ps.setInt(4, inv.getStock());
                    ps.setInt(5, inv.getIdInventario());
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(UPDATE_PRECIO)) {
                    ps.setDouble(1, inv.getPrecio());
                    ps.setInt(2, inv.getIdProducto());
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void eliminar(int id) throws Exception {
        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(DELETE_DETALLES)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public Inventario buscar(int id) throws Exception {
        Inventario inv = null;
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    inv = new Inventario();
                    inv.setIdInventario(rs.getInt("id_inventario"));
                    inv.setNombreProducto(rs.getString("nombre_comercial"));
                    inv.setColor(rs.getString("color"));
                    inv.setTalla(rs.getString("talla"));
                    inv.setStock(rs.getInt("stock"));
                    inv.setPrecio(rs.getDouble("precio_venta"));
                }
            }
        }
        return inv;
    }

    public void cargarProductos(JComboBox<String> combo) throws Exception {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCTOS);
             ResultSet rs = ps.executeQuery()) {
            combo.removeAllItems();
            while (rs.next()) {
                combo.addItem(
                    rs.getInt("id_producto") + " - " + rs.getString("nombre_comercial")
                );
            }
        }
    }
}