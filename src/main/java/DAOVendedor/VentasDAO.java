/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOVendedor;

import arbol.ArbolBBusqueda;
import conexion.Conexion;
import interfazVendedor.VentasInterfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import modeloVendedor.DetalleVenta;
import modeloVendedor.Ventas;

/**
 *
 * @author moise
 */
public class VentasDAO implements VentasInterfaz{
    
   private static final String INSERT_VENTA = "INSERT INTO ventas (id_cliente, id_vendedor, fecha_venta, total_venta) " + "VALUES (?, ?, ?, ?) RETURNING id_venta";
   private static final String INSERT_DETALLE = "INSERT INTO detalleventas (id_venta, id_inventario, cantidad, precio_unitario) " + "VALUES (?, ?, ?, ?)";
   private static final String SELECT_DETALLE = "SELECT id_detalleventa, id_venta, id_inventario, cantidad, precio_unitario " + "FROM detalleventas WHERE id_venta = ? ORDER BY id_detalleventa ASC";
   private static final String SELECT_CLIENTES = "SELECT id_cliente, nombre_cliente FROM clientes ORDER BY nombre_cliente ASC";
   private static final String SELECT_VENDEDORES = "SELECT id_vendedor, nombre_vendedor FROM vendedores ORDER BY nombre_vendedor ASC";
   private static final String SELECT_PRODUCTOS = "SELECT DISTINCT nombre_comercial FROM productos ORDER BY nombre_comercial ASC";
   private static final String SELECT_COLORES_Y_PRECIO = "SELECT DISTINCT i.color, p.precio_venta " + "FROM inventario i JOIN productos p ON i.id_producto = p.id_producto " + "WHERE p.nombre_comercial = ? AND i.stock > 0 ORDER BY i.color ASC";
   private static final String SELECT_TALLAS = "SELECT DISTINCT CAST(i.talla AS TEXT) AS talla " + "FROM inventario i JOIN productos p ON i.id_producto = p.id_producto " + "WHERE p.nombre_comercial = ? AND i.color = ? AND i.stock > 0 " + "ORDER BY talla ASC";
   private static final String SELECT_INVENTARIO = "SELECT i.id_inventario " + "FROM inventario i JOIN productos p ON i.id_producto = p.id_producto " + "WHERE p.nombre_comercial = ? AND i.color = ? AND i.talla = CAST(? AS NUMERIC)";
   private static final String UPDATE_STOCK = "UPDATE inventario SET stock = stock - ? WHERE id_inventario = ?";
   private static final String SELECT_STOCK = "SELECT stock FROM inventario WHERE id_inventario = ?"; 
   
   private ResultSet ejecutarConsulta(Connection conn, PreparedStatement ps) throws Exception {
        return ps.executeQuery();
    }
   
   public int obtenerStock(int idInventario) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_STOCK);
        ps.setInt(1, idInventario);
        ResultSet rs = ps.executeQuery();
        int stock = rs.next() ? rs.getInt("stock") : 0;
        rs.close(); ps.close(); conn.close();
        return stock;
    }
   
   public void descontarStock(int idInventario, int cantidad) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(UPDATE_STOCK);
        ps.setInt(1, cantidad);
        ps.setInt(2, idInventario);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    @Override
    public int registrarVenta(Ventas v) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(INSERT_VENTA);
        ps.setInt(1, v.getIdCliente());
        ps.setInt(2, v.getIdVendedor());
        ps.setTimestamp(3, java.sql.Timestamp.valueOf(v.getFecha() + " 00:00:00"));
        ps.setDouble(4, v.getTotal());
        ResultSet rs = ps.executeQuery();
        int idVenta = rs.next() ? rs.getInt(1) : 0;
        rs.close(); 
        ps.close(); 
        conn.close();
        return idVenta;
    }

    @Override
    public void registrarDetalle(DetalleVenta d) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(INSERT_DETALLE);
        ps.setInt(1, d.getIdVenta());
        ps.setInt(2, d.getIdInventario());
        ps.setInt(3, d.getCantidad());
        ps.setDouble(4, d.getPrecioUnitario());
        ps.executeUpdate();
        ps.close(); 
        conn.close();
    }

    @Override
    public ArbolBBusqueda<DetalleVenta> listarDetalle(int idVenta) throws Exception {
        ArbolBBusqueda<DetalleVenta> arbol = new ArbolBBusqueda<>();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_DETALLE);
        ps.setInt(1, idVenta);
        ResultSet rs = ejecutarConsulta(conn, ps);
        while (rs.next()) {
            DetalleVenta d = new DetalleVenta();
            d.setIdDetalleVenta(rs.getInt("id_detalleventa"));
            d.setIdVenta(rs.getInt("id_venta"));
            d.setIdInventario(rs.getInt("id_inventario"));
            d.setCantidad(rs.getInt("cantidad"));
            d.setPrecioUnitario(rs.getDouble("precio_unitario"));
            arbol.insertar(d);
        }
        rs.close(); 
        ps.close(); 
        conn.close();
        return arbol;
    }

    @Override
    public void cargarClientes(JComboBox<String> combo) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_CLIENTES);
        ResultSet rs = ejecutarConsulta(conn, ps);
        combo.removeAllItems();
        while (rs.next())
            combo.addItem(rs.getInt("id_cliente") + " - " + rs.getString("nombre_cliente"));
        rs.close(); 
        ps.close(); 
        conn.close();
    }

    @Override
    public void cargarVendedores(JComboBox<String> combo) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_VENDEDORES);
        ResultSet rs = ejecutarConsulta(conn, ps);
        combo.removeAllItems();
        while (rs.next())
            combo.addItem(rs.getInt("id_vendedor") + " - " + rs.getString("nombre_vendedor"));
        rs.close(); 
        ps.close(); 
        conn.close();
    }

    @Override
    public void cargarProductos(JComboBox<String> combo) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCTOS);
        ResultSet rs = ejecutarConsulta(conn, ps);
        combo.removeAllItems();
        while (rs.next())
            combo.addItem(rs.getString("nombre_comercial"));
        rs.close(); 
        ps.close(); 
        conn.close();
    }

    @Override
    public void cargarColoresPorProducto(JComboBox<String> combo, String producto) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_COLORES_Y_PRECIO);
        ps.setString(1, producto);
        ResultSet rs = ejecutarConsulta(conn, ps);
        combo.removeAllItems();
        while (rs.next())
            combo.addItem(rs.getString("color"));
        rs.close(); 
        ps.close(); 
        conn.close();
    }

    @Override
    public void cargarTallas(JComboBox<String> combo, String producto, String color) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_TALLAS);
        ps.setString(1, producto);
        ps.setString(2, color);
        ResultSet rs = ejecutarConsulta(conn, ps);
        combo.removeAllItems();
        while (rs.next())
            combo.addItem(rs.getString("talla"));
        rs.close(); 
        ps.close(); 
        conn.close();
    }
    
    public double cargarColoresYObtenerPrecio(javax.swing.JComboBox<String> combo, String producto) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_COLORES_Y_PRECIO);
        ps.setString(1, producto);
        ResultSet rs = ejecutarConsulta(conn, ps);
        combo.removeAllItems();
        double precio = 0;
        while (rs.next()) {
            combo.addItem(rs.getString("color"));
            precio = rs.getDouble("precio_venta");
        }
        rs.close(); ps.close(); conn.close();
        return precio;
    }

    @Override
    public double obtenerPrecio(String producto) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement("SELECT precio_venta FROM productos WHERE nombre_comercial = ?");
        ps.setString(1, producto);
        ResultSet rs = ps.executeQuery();
        double precio = rs.next() ? rs.getDouble("precio_venta") : 0;
        rs.close(); ps.close(); conn.close();
        return precio;
    }

    @Override
    public int obtenerIdInventario(String producto, String color, String talla) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_INVENTARIO);
        ps.setString(1, producto);
        ps.setString(2, color);
        ps.setString(3, talla);
        ResultSet rs = ejecutarConsulta(conn, ps);
        int id = rs.next() ? rs.getInt("id_inventario") : 0;
        rs.close(); ps.close(); conn.close();
        return id;
    }
}