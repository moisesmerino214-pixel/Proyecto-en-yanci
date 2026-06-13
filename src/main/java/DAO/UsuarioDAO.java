/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import arbol.ArbolBBusqueda;
import conexion.Conexion;
import interfaz.UsuarioInterfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Usuario;
/**
 *
 * @author moise
 */
public class UsuarioDAO implements UsuarioInterfaz{
    private static final String SELECT_ALL = "SELECT u.id_usuario, u.nombre_usuario, u.contrasena, u.id_rol, " + "u.id_vendedor, v.nombre_vendedor " + "FROM usuarios u " + "LEFT JOIN vendedores v ON v.id_vendedor = u.id_vendedor";

    @Override
    public ArbolBBusqueda<Usuario> listar() throws Exception {
        ArbolBBusqueda<Usuario> arbol = new ArbolBBusqueda<>();
        
        Connection conn = Conexion.getConexion();
        conn.setAutoCommit(false);
        
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNombreUsuario(rs.getString("nombre_usuario"));
            usuario.setContrasena(rs.getString("contrasena")); 
            usuario.setIdRol(rs.getInt("id_rol"));
            usuario.setIdVendedor(rs.getInt("id_vendedor"));
            usuario.setNombreVendedor(rs.getString("nombre_vendedor"));
            arbol.insertar(usuario);
        }
        
        rs.close();
        ps.close();
        conn.close();
        
        return arbol;
    }
    
}
