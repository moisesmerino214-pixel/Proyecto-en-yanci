/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import arbol.ArbolBBusqueda;
import conexion.Conexion;
import interfaz.ClienteInterfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Cliente;

/**
 *
 * @author moise
 */
public class ClienteDAO implements ClienteInterfaz{
    private static final String SELECT_ALL = "SELECT id_cliente, nombre_cliente, dui, telefono, correo FROM clientes ORDER BY id_cliente ASC";
    private static final String INSERT = "INSERT INTO clientes (nombre_cliente, dui, telefono, correo) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE clientes SET nombre_cliente=?, dui=?, telefono=?, correo=? WHERE id_cliente=?";
    private static final String DELETE = "DELETE FROM clientes WHERE id_cliente=?";
    
    @Override
    public ArbolBBusqueda<Cliente> listar() throws Exception {
        ArbolBBusqueda<Cliente> arbol = new ArbolBBusqueda<>();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Cliente c = new Cliente();
            c.setIdCliente(rs.getInt("id_cliente"));
            c.setNombreCliente(rs.getString("nombre_cliente"));
            c.setDui(rs.getString("dui"));
            c.setTelefono(rs.getString("telefono"));
            c.setCorreo(rs.getString("correo"));
            arbol.insertar(c);
        }
        
        rs.close();
        ps.close();
        conn.close();
        return arbol;
    }
    
    @Override
    public void agregar(Cliente c) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(INSERT);
        ps.setString(1, c.getNombreCliente());
        ps.setString(2, c.getDui());
        ps.setString(3, c.getTelefono());
        ps.setString(4, c.getCorreo());
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    @Override
    public void modificar(Cliente c) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(UPDATE);
        ps.setString(1, c.getNombreCliente());
        ps.setString(2, c.getDui());
        ps.setString(3, c.getTelefono());
        ps.setString(4, c.getCorreo());
        ps.setInt(5, c.getIdCliente());
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    @Override
    public void eliminar(int id) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(DELETE);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
}
