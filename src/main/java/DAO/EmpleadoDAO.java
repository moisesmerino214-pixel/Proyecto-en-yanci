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

/**
 *
 * @author moise
 */
public class EmpleadoDAO implements EmpleadoInterfaz {
    private static final String INSERT_EMPLEADO = "INSERT INTO vendedores (nombre_vendedor, telefono, correo) VALUES (?, ?, ?)";
    private static final String UPDATE_EMPLEADO = "UPDATE vendedores SET nombre_vendedor=?, telefono=?, correo=? WHERE id_vendedor=?";
    private static final String DELETE_EMPLEADO = "DELETE FROM vendedores WHERE id_vendedor=?";
    private static final String SELECT_ALL_EMPLEADOS = "SELECT id_vendedor, nombre_vendedor, telefono, correo FROM vendedores ORDER BY id_vendedor ASC";
    
    @Override
    public boolean agregar(Empleado emp) throws Exception {
        Conexion con = new Conexion();
        try (Connection conn = con.getConexion();
             PreparedStatement ps = conn.prepareStatement(INSERT_EMPLEADO)) {
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getTelefono());
            ps.setString(3, emp.getCorreo());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean modificar(Empleado emp) throws Exception {
        Conexion con = new Conexion();
        try (Connection conn = con.getConexion();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EMPLEADO)) {
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getTelefono());
            ps.setString(3, emp.getCorreo());
            ps.setInt(4, emp.getIdEmpleado());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        Conexion con = new Conexion();
        try (Connection conn = con.getConexion();
             PreparedStatement ps = conn.prepareStatement(DELETE_EMPLEADO)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public ArbolBBusqueda<Empleado> listar() throws Exception {
        ArbolBBusqueda<Empleado> arbol = new ArbolBBusqueda<>();
        Conexion con = new Conexion();
        try (Connection conn = con.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_EMPLEADOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_vendedor"));
                emp.setNombre(rs.getString("nombre_vendedor"));
                emp.setTelefono(rs.getString("telefono"));
                emp.setCorreo(rs.getString("correo"));
                emp.setCargo("Vendedor"); 
                arbol.insertar(emp);
            }
        }
        return arbol;
    }
}