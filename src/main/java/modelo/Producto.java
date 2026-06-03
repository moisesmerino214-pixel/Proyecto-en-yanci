/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moise
 */
public class Producto implements Comparable <Producto> {
    private int    idProductoBD;
     private int idProducto;
     private String modelo;
    private String talla;
    private int stock;
    private String marca;
    private String color;
    private double precio;
 
    public Producto() {}

    public Producto(int idProducto,int idProductoBD, String modelo, String talla, int stock, String marca, String color, double precio) {
        this.idProducto = idProducto;
        this.idProductoBD = idProductoBD;
        this.talla = talla;
        this.stock = stock;
        this.marca = marca;
        this.color = color;
        this.precio = precio;
        this.modelo = modelo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdProductoBD() {
        return idProductoBD;
    }

    public void setIdProductoBD(int idProductoBD) {
        this.idProductoBD = idProductoBD;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
 
  
    
    /*
    @Override
    public String toString() {
        return modelo + ", talla " + talla + ", $" + precio;
    }
*/
    @Override
    public int compareTo(Producto o) {
        return Integer.compare(this.idProducto, o.idProducto);
        /*Zapato actual = this;
        if(actual.getModelo()<o.get()) {
            return -1;
        }else if(actual.getEdad()==o.getEdad()) {
        return 0;
        } else {
                return 1;
                }*/
    }
 
    
    
    
}
