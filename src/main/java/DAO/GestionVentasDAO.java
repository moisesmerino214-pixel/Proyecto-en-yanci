/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import arbol.ArbolBBusqueda;
import conexion.Conexion;
import interfaz.GestionVentasInterfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modeloVendedor.DetalleVenta;
import modeloVendedor.Ventas;
/**
 *
 * @author moise
 */
public class GestionVentasDAO implements GestionVentasInterfaz {
    private static final String SELECT_TODAS = "SELECT v.id_venta, c.nombre_cliente, ve.nombre_vendedor, v.fecha_venta, v.total_venta " + "FROM ventas v " + "JOIN clientes c ON v.id_cliente = c.id_cliente " + "JOIN vendedores ve ON v.id_vendedor = ve.id_vendedor " + "ORDER BY v.id_venta DESC";
    private static final String SELECT_POR_CLIENTE = "SELECT v.id_venta, c.nombre_cliente, ve.nombre_vendedor, v.fecha_venta, v.total_venta " + "FROM ventas v " + "JOIN clientes c ON v.id_cliente = c.id_cliente " + "JOIN vendedores ve ON v.id_vendedor = ve.id_vendedor " + "WHERE LOWER(c.nombre_cliente) LIKE LOWER(?) ORDER BY v.id_venta DESC";
    private static final String SELECT_POR_VENDEDOR = "SELECT v.id_venta, c.nombre_cliente, ve.nombre_vendedor, v.fecha_venta, v.total_venta " + "FROM ventas v " + "JOIN clientes c ON v.id_cliente = c.id_cliente " + "JOIN vendedores ve ON v.id_vendedor = ve.id_vendedor " + "WHERE LOWER(ve.nombre_vendedor) LIKE LOWER(?) ORDER BY v.id_venta DESC";
    private static final String SELECT_POR_FECHA = "SELECT v.id_venta, c.nombre_cliente, ve.nombre_vendedor, v.fecha_venta, v.total_venta " + "FROM ventas v " + "JOIN clientes c ON v.id_cliente = c.id_cliente " + "JOIN vendedores ve ON v.id_vendedor = ve.id_vendedor " + "WHERE CAST(v.fecha_venta AS DATE) = ? ORDER BY v.id_venta DESC";
    private static final String SELECT_DETALLE = "SELECT dv.id_detalleventa, dv.id_inventario, p.nombre_comercial, i.color, i.talla, " + "dv.cantidad, dv.precio_unitario, (dv.cantidad * dv.precio_unitario) AS subtotal " + "FROM detalleventas dv " + "JOIN inventario i ON dv.id_inventario = i.id_inventario " + "JOIN productos p ON i.id_producto = p.id_producto " + "WHERE dv.id_venta = ?";
    private static final String DELETE_DETALLE = "DELETE FROM detalleventas WHERE id_venta = ?";
    private static final String DELETE_VENTA = "DELETE FROM ventas WHERE id_venta = ?";
    private static final String RESTAURAR_STOCK = "UPDATE inventario SET stock = stock + ? WHERE id_inventario = ?";
    
    @Override
    public ArbolBBusqueda<Ventas> listarVentas() throws Exception {
        ArbolBBusqueda<Ventas> arbol = new ArbolBBusqueda<>();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_TODAS);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Ventas v = new Ventas();
            v.setIdVenta(rs.getInt("id_venta"));
            v.setFecha(rs.getTimestamp("fecha_venta").toString());
            v.setTotal(rs.getDouble("total_venta"));
            v.setNombreCliente(rs.getString("nombre_cliente"));
            v.setNombreVendedor(rs.getString("nombre_vendedor"));
            arbol.insertar(v);
        }
        rs.close(); ps.close(); conn.close();
        return arbol;
    }

    @Override
    public ArbolBBusqueda<Ventas> buscarPorCliente(String criterio) throws Exception {
        return ejecutarBusqueda(SELECT_POR_CLIENTE, criterio, false);
    }

    @Override
    public ArbolBBusqueda<Ventas> buscarPorVendedor(String criterio) throws Exception {
        return ejecutarBusqueda(SELECT_POR_VENDEDOR, criterio, false);
    }

    @Override
    public ArbolBBusqueda<Ventas> buscarPorFecha(String fecha) throws Exception {
        return ejecutarBusqueda(SELECT_POR_FECHA, fecha, true);
    }

    private ArbolBBusqueda<Ventas> ejecutarBusqueda(String query, String param, boolean esFecha) throws Exception {
        ArbolBBusqueda<Ventas> arbol = new ArbolBBusqueda<>();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(query);
        if (esFecha)
            ps.setDate(1, java.sql.Date.valueOf(param));
        else
            ps.setString(1, "%" + param + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Ventas v = new Ventas();
            v.setIdVenta(rs.getInt("id_venta"));
            v.setFecha(rs.getTimestamp("fecha_venta").toString());
            v.setTotal(rs.getDouble("total_venta"));
            v.setNombreCliente(rs.getString("nombre_cliente"));
            v.setNombreVendedor(rs.getString("nombre_vendedor"));
            arbol.insertar(v);
        }
        rs.close(); ps.close(); conn.close();
        return arbol;
    }

    @Override
    public ArbolBBusqueda<DetalleVenta> listarDetalle(int idVenta) throws Exception {
        ArbolBBusqueda<DetalleVenta> arbol = new ArbolBBusqueda<>();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_DETALLE);
        ps.setInt(1, idVenta);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DetalleVenta d = new DetalleVenta();
            d.setIdDetalleVenta(rs.getInt("id_detalleventa"));
            d.setIdInventario(rs.getInt("id_inventario"));
            d.setProducto(rs.getString("nombre_comercial"));
            d.setColor(rs.getString("color"));
            d.setTalla(rs.getString("talla"));
            d.setCantidad(rs.getInt("cantidad"));
            d.setPrecioUnitario(rs.getDouble("precio_unitario"));
            arbol.insertar(d);
        }
        rs.close(); ps.close(); conn.close();
        return arbol;
    }

    @Override
    public void anularVenta(int idVenta) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement psDetalle = conn.prepareStatement( "SELECT id_inventario, cantidad FROM detalleventas WHERE id_venta = ?");
        psDetalle.setInt(1, idVenta);
        ResultSet rs = psDetalle.executeQuery();
        while (rs.next()) {
            PreparedStatement psStock = conn.prepareStatement(RESTAURAR_STOCK);
            psStock.setInt(1, rs.getInt("cantidad"));
            psStock.setInt(2, rs.getInt("id_inventario"));
            psStock.executeUpdate();
            psStock.close();
        }
        rs.close(); psDetalle.close();
        PreparedStatement ps1 = conn.prepareStatement(DELETE_DETALLE);
        ps1.setInt(1, idVenta); ps1.executeUpdate(); ps1.close();
        PreparedStatement ps2 = conn.prepareStatement(DELETE_VENTA);
        ps2.setInt(1, idVenta); ps2.executeUpdate(); ps2.close();
        conn.close();
    }
}

