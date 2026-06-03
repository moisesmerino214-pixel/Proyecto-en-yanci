/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbol;

import java.util.ArrayList;

/**
 *
 * @author Armando
 */
public class ArbolBinario<T> {

    private Nodo raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    protected ArrayList<T> preOrdenNID(Nodo r, ArrayList a) {
        if (r != null) {
            a.add(r.getDato());//N
            preOrdenNID(r.getrIzda(), a);//I
            preOrdenNID(r.getrDrch(), a);//D
        }
        return a;
    }

    protected ArrayList<T> inOrdenIND(Nodo r, ArrayList a) {
        if (r != null) {

            inOrdenIND(r.getrIzda(), a);//I
            a.add(r.getDato());//N
            inOrdenIND(r.getrDrch(), a);//D
        }
        return a;
    }

    protected ArrayList<T> postOrdenIND(Nodo r, ArrayList a) {
        if (r != null) {

            postOrdenIND(r.getrIzda(), a);//I
            postOrdenIND(r.getrDrch(), a);//D
            a.add(r.getDato());//N
        }
        return a;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public <T extends Comparable> Nodo buscar(T dato) {
        return buscar(dato, raiz);
    }

    private <T extends Comparable> Nodo buscar(T dato, Nodo r) {
        if (r == null) {
            return null;
        } else if (dato.compareTo(r.getDato()) < 0) {
            return buscar(dato, r.getrIzda());
        } else if (dato.compareTo(r.getDato()) > 0) {
            return buscar(dato, r.getrDrch());
        } else {
            return r;
        }
    }
}
