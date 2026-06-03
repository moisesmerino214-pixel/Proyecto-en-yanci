/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import arbol.ArbolBBusqueda;
import conexion.Conexion;
import interfaz.ProveedorInterfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Proveedor;

/**
 *
 * @author moise
 */
public class ProveedorDAO implements ProveedorInterfaz {
    private static final String INSERT_PROVEEDOR = "INSERT INTO proveedores (nombre_empresa, nombre_proveedor, telefono, correo) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PROVEEDOR = "UPDATE proveedores SET nombre_empresa=?, nombre_proveedor=?, telefono=?, correo=? WHERE id_proveedor=?";
    private static final String DELETE_PROVEEDOR = "DELETE FROM proveedores WHERE id_proveedor=?";
    private static final String SELECT_ALL_PROVEEDORES = "SELECT id_proveedor, nombre_empresa, nombre_proveedor, telefono, correo FROM proveedores ORDER BY id_proveedor ASC";

    @Override
    public boolean agregar(Proveedor prov) throws Exception {
        Conexion con = new Conexion();
        try (Connection conn = con.getConexion();
             PreparedStatement ps = conn.prepareStatement(INSERT_PROVEEDOR)) {
            ps.setString(1, prov.getNombreEmpresa());
            ps.setString(2, prov.getNombreProveedor());
            ps.setString(3, prov.getTelefono());
            ps.setString(4, prov.getCorreo());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean modificar(Proveedor prov) throws Exception {
        Conexion con = new Conexion();
        try (Connection conn = con.getConexion();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PROVEEDOR)) {
            ps.setString(1, prov.getNombreEmpresa());
            ps.setString(2, prov.getNombreProveedor());
            ps.setString(3, prov.getTelefono());
            ps.setString(4, prov.getCorreo());
            ps.setInt(5, prov.getIdProveedor()); // Índice 5 correcto (son 5 signos '?')
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        Conexion con = new Conexion();
        try (Connection conn = con.getConexion();
             PreparedStatement ps = conn.prepareStatement(DELETE_PROVEEDOR)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public ArbolBBusqueda<Proveedor> listar() throws Exception {
        ArbolBBusqueda<Proveedor> arbol = new ArbolBBusqueda<>();
        Conexion con = new Conexion();
        try (Connection conn = con.getConexion();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PROVEEDORES);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Proveedor prov = new Proveedor();
                prov.setIdProveedor(rs.getInt("id_proveedor"));
                prov.setNombreEmpresa(rs.getString("nombre_empresa"));
                prov.setNombreProveedor(rs.getString("nombre_provider" != null ? "nombre_proveedor" : "nombre_proveedor")); // Ajustado a tu BD
                prov.setTelefono(rs.getString("telefono"));
                prov.setCorreo(rs.getString("correo"));
                
                arbol.insertar(prov);
            }
        }
        return arbol;
    }
}
