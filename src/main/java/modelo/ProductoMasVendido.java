/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moise
 */
public class ProductoMasVendido implements Comparable<ProductoMasVendido> {
    private int id;
    private String nombre;
    private double precio;
    private int paresVendidos;
    
    public ProductoMasVendido() {
    }

    public ProductoMasVendido(int id, String nombre, double precio, int paresVendidos) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.paresVendidos = paresVendidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getParesVendidos() {
        return paresVendidos;
    }

    public void setParesVendidos(int paresVendidos) {
        this.paresVendidos = paresVendidos;
    }   
    
    @Override
    public int compareTo(ProductoMasVendido o) {
        int cmp = Integer.compare(o.paresVendidos, this.paresVendidos);
        if (cmp == 0) return Integer.compare(this.id, o.id);
        return cmp;
    }
    
}
