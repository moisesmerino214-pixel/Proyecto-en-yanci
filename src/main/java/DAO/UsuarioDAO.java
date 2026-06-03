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
    private static final String SELECT_ALL = "SELECT id_usuario, nombre_usuario, contrasena, id_rol FROM usuarios ORDER BY nombre_usuario";

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
            arbol.insertar(usuario);
        }
        
        rs.close();
        ps.close();
        conn.close();
        
        return arbol;
    }
    
}
