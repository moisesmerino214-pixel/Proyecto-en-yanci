/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import arbol.ArbolBBusqueda;
import conexion.Conexion;
import interfaz.EmpleadoInterfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Empleado;
import utilidades.Encriptar;

/**
 *
 * @author moise
 */
public class EmpleadoDAO implements EmpleadoInterfaz {
    private static final String INSERT_VENDEDOR = "INSERT INTO vendedores (nombre_vendedor, telefono, correo) VALUES (?, ?, ?) RETURNING id_vendedor";
    private static final String INSERT_USUARIO  = "INSERT INTO usuarios (id_rol, nombre_usuario, contrasena, id_vendedor) VALUES (2, ?, ?, ?)";
    private static final String UPDATE_VENDEDOR  = "UPDATE vendedores SET nombre_vendedor=?, telefono=?, correo=? WHERE id_vendedor=?";
    private static final String UPDATE_USUARIO    = "UPDATE usuarios SET nombre_usuario=? WHERE id_vendedor=?";
    private static final String UPDATE_CONTRASENA = "UPDATE usuarios SET contrasena=? WHERE id_vendedor=?";
    private static final String DELETE_VENDEDOR  = "DELETE FROM vendedores WHERE id_vendedor=?";
    private static final String SELECT_ALL = "SELECT v.id_vendedor, v.nombre_vendedor, v.telefono, v.correo, u.nombre_usuario " + "FROM vendedores v " + "JOIN usuarios u ON u.id_vendedor = v.id_vendedor " + "ORDER BY v.id_vendedor ASC"; 
   
    @Override
    public boolean agregar(Empleado emp, String nombreUsuario, String contrasena) throws Exception {
        Connection conn = Conexion.getConexion();
        conn.setAutoCommit(false);
        try {
            PreparedStatement ps1 = conn.prepareStatement(INSERT_VENDEDOR);
            ps1.setString(1, emp.getNombre());
            ps1.setString(2, emp.getTelefono());
            ps1.setString(3, emp.getCorreo());
            ResultSet rs = ps1.executeQuery();
            rs.next();
            int idVendedor = rs.getInt("id_vendedor");
            ps1.close();
            
            String hash = Encriptar.getStringMessageDigest(contrasena, Encriptar.SHA256);
            PreparedStatement ps2 = conn.prepareStatement(INSERT_USUARIO);
            ps2.setString(1, nombreUsuario);
            ps2.setString(2, hash);
            ps2.setInt(3, idVendedor);
            ps2.executeUpdate();
            ps2.close();

            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Override
    public boolean modificar(Empleado emp, String nuevaContrasena) throws Exception {
        Connection conn = Conexion.getConexion();
        conn.setAutoCommit(false);
        try {
            PreparedStatement ps1 = conn.prepareStatement(UPDATE_VENDEDOR);
            ps1.setString(1, emp.getNombre());
            ps1.setString(2, emp.getTelefono());
            ps1.setString(3, emp.getCorreo());
            ps1.setInt(4, emp.getIdEmpleado());
            ps1.executeUpdate();
            ps1.close();
            
            PreparedStatement ps2 = conn.prepareStatement(UPDATE_USUARIO);
            ps2.setString(1, emp.getNombreUsuario());
            ps2.setInt(2, emp.getIdEmpleado());
            ps2.executeUpdate();
            ps2.close();

            if (nuevaContrasena != null && !nuevaContrasena.trim().isEmpty()) {
                String hash = Encriptar.getStringMessageDigest(nuevaContrasena, Encriptar.SHA256);
                PreparedStatement ps3 = conn.prepareStatement(UPDATE_CONTRASENA);
                ps3.setString(1, hash);
                ps3.setInt(2, emp.getIdEmpleado());
                ps3.executeUpdate();
                ps3.close();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        Connection conn = new Conexion().getConexion();
        PreparedStatement ps = conn.prepareStatement(DELETE_VENDEDOR);
        ps.setInt(1, id);
        int filas = ps.executeUpdate();
        ps.close();
        conn.close();
        return filas > 0;
    }
    
    @Override
    public ArbolBBusqueda<Empleado> listar() throws Exception {
        ArbolBBusqueda<Empleado> arbol = new ArbolBBusqueda<>();
        Connection conn = new Conexion().getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Empleado emp = new Empleado();
            emp.setIdEmpleado(rs.getInt("id_vendedor"));
            emp.setNombre(rs.getString("nombre_vendedor"));
            emp.setTelefono(rs.getString("telefono"));
            emp.setCorreo(rs.getString("correo"));
            emp.setCargo("Vendedor");
            emp.setNombreUsuario(rs.getString("nombre_usuario"));
            arbol.insertar(emp);
        }
        rs.close();
        ps.close();
        conn.close();
        return arbol;
    }
}