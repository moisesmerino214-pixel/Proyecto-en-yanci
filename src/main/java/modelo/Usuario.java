/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moise
 */
public class Usuario implements Comparable<Usuario>{
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private int idRol;
    private int idVendedor;
    private String nombreVendedor;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String contrasena, int idRol, int idVendedor, String nombreVendedor) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.idRol = idRol;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    @Override
    public int compareTo(Usuario otro) {
        return this.nombreUsuario.compareTo(otro.getNombreUsuario());
    }
    
    
}
