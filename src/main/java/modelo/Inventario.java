/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moise
 */
public class Inventario implements Comparable<Inventario> {
    
    private int idInventario;
    private int idProducto;
    private String nombreProducto;
    private String color;
    private String talla;
    private int stock;
    private double precio;

    public Inventario() {
    }

    public Inventario(int idInventario, int idProducto, String nombreProducto, String color, String talla, int stock, double precio) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.color = color;
        this.talla = talla;
        this.stock = stock;
        this.precio = precio;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    
    
    
    @Override
    public int compareTo(Inventario o) {
        return Integer.compare(this.idInventario, o.idInventario);
    }
    
}
