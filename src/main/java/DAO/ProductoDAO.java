/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import arbol.ArbolBBusqueda;
import conexion.Conexion;
import interfaz.ProductoInterfaz;
import modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;


/**
 *
 * @author moise
 */
public class ProductoDAO implements ProductoInterfaz {
    private static final String SELECT_ALL = "SELECT i.id_inventario, p.modelo, i.talla, i.stock, p.marca, i.color, p.precio_venta, i.id_producto " + "FROM inventario i JOIN productos p ON i.id_producto = p.id_producto " + "ORDER BY i.id_inventario ASC";
    private static final String SELECT_BY_ID = "SELECT i.id_inventario, p.modelo, i.talla, i.stock, p.marca, i.color, p.precio_venta, i.id_producto " + "FROM inventario i JOIN productos p ON i.id_producto = p.id_producto " + "WHERE i.id_inventario = ?";
    private static final String INSERT = "INSERT INTO inventario (id_producto, color, talla, stock) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_INVENTARIO = "UPDATE inventario SET color=?, talla=?, stock=? WHERE id_inventario=?";
    private static final String UPDATE_PRODUCTO = "UPDATE productos SET modelo=?, precio_venta=? WHERE id_producto=?";
    private static final String DELETE = "DELETE FROM inventario WHERE id_inventario=?";
    private static final String SELECT_PRODUCTOS = "SELECT id_producto, nombre_comercial, marca FROM productos ORDER BY nombre_comercial ASC";
    private static final String SELECT_COLORES = "SELECT DISTINCT color FROM inventario ORDER BY color ASC";
    
    @Override
    public ArbolBBusqueda<Producto> listar() throws Exception {
        ArbolBBusqueda<Producto> arbol = new ArbolBBusqueda<>();
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_inventario"));
                p.setIdProductoBD(rs.getInt("id_producto"));
                p.setModelo(rs.getString("modelo"));
                p.setTalla(rs.getString("talla"));
                p.setStock(rs.getInt("stock"));
                p.setMarca(rs.getString("marca"));
                p.setColor(rs.getString("color"));
                p.setPrecio(rs.getDouble("precio_venta"));
                arbol.insertar(p);
            }
        }
        return arbol;
    }
 
    @Override
    public Producto buscar(int id) throws Exception {
        Producto p = null;
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Producto();
                    p.setIdProducto(rs.getInt("id_inventario"));
                    p.setIdProductoBD(rs.getInt("id_producto"));
                    p.setModelo(rs.getString("modelo"));
                    p.setTalla(rs.getString("talla"));
                    p.setStock(rs.getInt("stock"));
                    p.setMarca(rs.getString("marca"));
                    p.setColor(rs.getString("color"));
                    p.setPrecio(rs.getDouble("precio_venta"));
                }
            }
        }
        return p;
    }
 
    @Override
    public void agregar(Producto p) throws Exception {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setInt(1, p.getIdProductoBD());
            ps.setString(2, p.getColor());
            ps.setDouble(3, Double.parseDouble(p.getTalla()));
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
        }
    }
 
    @Override
    public void modificar(Producto p) throws Exception {
        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement psInv = conn.prepareStatement(UPDATE_INVENTARIO)) {
                    psInv.setString(1, p.getColor());
                    psInv.setDouble(2, Double.parseDouble(p.getTalla()));
                    psInv.setInt(3, p.getStock());
                    psInv.setInt(4, p.getIdProducto());
                    psInv.executeUpdate();
                }
                try (PreparedStatement psProd = conn.prepareStatement(UPDATE_PRODUCTO)) {
                    psProd.setString(1, p.getModelo());
                    psProd.setDouble(2, p.getPrecio());
                    psProd.setInt(3, p.getIdProductoBD());
                    psProd.executeUpdate();
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
 
    @Override
    public void eliminar(int id) throws Exception {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
 
    @Override
    public void cargarMarcas(JComboBox<String> combo) throws Exception {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCTOS);
             ResultSet rs = ps.executeQuery()) {
            combo.removeAllItems();
            while (rs.next()) {
                combo.addItem(
                    rs.getInt("id_producto") + " - " +
                    rs.getString("nombre_comercial") + " (" +
                    rs.getString("marca") + ")"
                );
            }
        }
    }
 
    @Override
    public void cargarColores(JComboBox<String> combo) throws Exception {
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_COLORES);
             ResultSet rs = ps.executeQuery()) {
            combo.removeAllItems();
            while (rs.next()) {
                combo.addItem(rs.getString("color"));
            }
        }
    }
}