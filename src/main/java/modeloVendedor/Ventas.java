/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloVendedor;

/**
 *
 * @author moise
 */
public class Ventas implements Comparable<Ventas> {
    private int idVenta;
    private int idCliente;
    private int idVendedor;
    private String fecha;       
    private double total;
    private String nombreCliente;
    private String nombreVendedor;

    public Ventas() {
    }

    public Ventas(int idVenta, int idCliente, int idVendedor, String fecha, double total) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idVendedor = idVendedor;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public int compareTo(Ventas o) {
        return Integer.compare(this.idVenta, o.idVenta);
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }
    
    

    @Override
    public String toString() {
        return idVenta + "";
    }

    
}
