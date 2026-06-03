/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbol;

/**
 *
 * @author Armando
 */
public class Nodo<T> {

    private T dato;
    private Nodo rIzda;
    private Nodo rDrch;
    private int alt;

    public Nodo(T dato) {
        this.dato = dato;
        this.rIzda = null;
        this.rDrch = null;
        alt = 0;
    }

    public Nodo(T dato, Nodo rIzda, Nodo rDrch) {
        this.dato = dato;
        this.rIzda = rIzda;
        this.rDrch = rDrch;
        alt = 0;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo getrIzda() {
        return rIzda;
    }

    public void setrIzda(Nodo rIzda) {
        this.rIzda = rIzda;
    }

    public Nodo getrDrch() {
        return rDrch;
    }

    public void setrDrch(Nodo rDrch) {
        this.rDrch = rDrch;
    }

    public int getAlt() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }
    
    

    @Override
    public String toString() {
        return dato +"";
    }

}
