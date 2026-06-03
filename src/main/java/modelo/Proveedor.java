/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moise
 */
public class Proveedor implements Comparable<Proveedor> {
    private int idProveedor;
    private String nombreEmpresa;
    private String nombreProveedor;
    private String telefono;
    private String correo;

    public Proveedor() {
    }

    public Proveedor(int idProveedor, String nombreEmpresa, String nombreProveedor, String telefono, String correo) {
        this.idProveedor = idProveedor;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreProveedor = nombreProveedor;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
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
    public int compareTo(Proveedor o) {
       if (this.idProveedor == o.idProveedor) {
            return 0;
        } else if (this.idProveedor > o.idProveedor) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Proveedor{" + "idProveedor=" + idProveedor + ", nombreEmpresa=" + nombreEmpresa + ", nombreProveedor=" + nombreProveedor + '}';
    }
   
    
    
}
