/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moise
 */
public class Cliente implements Comparable<Cliente> {
    private int idCliente;
    private String nombreCliente;
    private String dui;
    private String telefono;
    private String correo;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombreCliente, String dui, String telefono, String correo) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.dui = dui;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public int compareTo(Cliente o) {
        return Integer.compare(this.idCliente, o.idCliente);
    }
    
    
    
}
