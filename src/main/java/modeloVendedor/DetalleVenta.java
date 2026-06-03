/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloVendedor;

/**
 *
 * @author moise
 */
public class DetalleVenta implements Comparable<DetalleVenta> {
    private int idDetalleVenta;   
    private int idVenta;          
    private int idInventario;    
    private int cantidad;
    private double precioUnitario;
    private String producto;
    private String color;
    private String talla;
    

    public DetalleVenta() {
    }

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }
    
    public double getSubtotal() {
        return precioUnitario * cantidad;
    }

    @Override
    public int compareTo(DetalleVenta o) {
        return Integer.compare(this.idDetalleVenta, o.idDetalleVenta);
    }

    @Override
    public String toString() {
        return idDetalleVenta + "";
    }
    
    
    
}
