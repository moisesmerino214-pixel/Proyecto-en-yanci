/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moise
 */
public class Empleado implements Comparable<Empleado>{
    private int idEmpleado;
    private String nombre;
    private String telefono;
    private String correo;
    private String cargo; 
    private String nombreUsuario;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String nombre, String telefono, String correo, String cargo, String nombreUsuario) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.cargo = cargo;
        this.nombreUsuario = nombreUsuario;
    }

    

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    

    @Override
    public int compareTo(Empleado o) {
        return Integer.compare(this.idEmpleado, o.idEmpleado);
    }
   
   
}
