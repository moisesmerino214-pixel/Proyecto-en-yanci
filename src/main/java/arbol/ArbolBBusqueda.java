/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbol;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Armando
 */
public class ArbolBBusqueda<T> extends ArbolBinario {

    public ArbolBBusqueda() {
        super();
    }

    public <T extends Comparable> void insertar(T dato) {
        super.setRaiz(insertar(dato, super.getRaiz()));
    }

    private <T extends Comparable> Nodo insertar(T dato, Nodo r) {
        if (r == null) {
            r = new Nodo(dato);
        } else if (dato.compareTo(r.getDato()) < 0) {
            Nodo izd;
            izd = insertar(dato, r.getrIzda());
            r.setrIzda(izd);
        } else if (dato.compareTo(r.getDato()) > 0) {
            Nodo drch;
            drch = insertar(dato, r.getrDrch());
            r.setrDrch(drch);
        } else {
            System.out.println("Duplicado");
        }
        return r;
    }

    public <T extends Comparable> void eliminar(T dato) {
        super.setRaiz(eliminar(dato, super.getRaiz()));
    }

    private <T extends Comparable> Nodo eliminar(T dato, Nodo r) {
        if (r == null) {
            JOptionPane.showMessageDialog(null, "No hay nodo a eliminar");
        } else if (dato.compareTo(r.getDato()) < 0) {
            Nodo izq;
            izq = eliminar(dato, r.getrIzda());
            r.setrIzda(izq);
        } else if (dato.compareTo(r.getDato()) > 0) {
            Nodo drch;
            drch = eliminar(dato, r.getrDrch());
            r.setrDrch(drch);
        } else {
            Nodo q;
            q = r;
            if (q.getrIzda() == null) {//solo tiene un hijo derecho
                r = q.getrDrch();

            } else if (q.getrDrch() == null) {//solo tiene un hijo izquierdo
                r = q.getrIzda();
            } else {//hay dos hijos o mas ramas
                q = reemplazar(q);
            }
            q = null;
        }
        return r;
    }

    private Nodo reemplazar(Nodo actual) {
        Nodo aux, ant;
        ant = actual;
        aux = actual.getrIzda();
        while (aux.getrDrch() != null) {
            ant = aux;
            aux = aux.getrDrch();
        }
        actual.setDato(aux.getDato());
        if (ant == actual) {
            ant.setrIzda(aux.getrIzda());
        } else {
            ant.setrDrch(aux.getrIzda());
        }
        return aux;
    }

    public ArrayList IND() {
        ArrayList a = new ArrayList();
        return super.inOrdenIND(super.getRaiz(), a);
    }

    public ArrayList NID() {
        ArrayList a = new ArrayList();
        return super.preOrdenNID(super.getRaiz(), a);
    }

    public ArrayList PostIND() {
        ArrayList a = new ArrayList();
        return super.postOrdenIND(super.getRaiz(), a);
    }

// Ejercicio 1: Calcular el peso del árbol (cantidad de nodos)
    public int peso() {
        return peso(super.getRaiz());
    }

    private int peso(Nodo r) {
        if (r == null) {
            return 0;
        }
        return 1 + peso(r.getrIzda()) + peso(r.getrDrch());
    }

// Ejercicio 2: Mostrar nodos que tienen dos hijos
    public void mostrarNodosDosHijos() {
        mostrarNodosDosHijos(super.getRaiz());
    }

    private void mostrarNodosDosHijos(Nodo r) {
        if (r != null) {
            if (r.getrIzda() != null && r.getrDrch() != null) {
                System.out.println(r.getDato());
            }
            mostrarNodosDosHijos(r.getrIzda());
            mostrarNodosDosHijos(r.getrDrch());
        }
    }

    //    ejercicio 3
    public T maximo() {
        if (super.getRaiz() == null) {
            return null;
        }
        Nodo r = super.getRaiz();
        while (r.getrDrch() != null) {
            r = r.getrDrch();
        }
        return (T) r.getDato();
    }

//    ejercicio 4
    public T minimo() {
        if (super.getRaiz() == null) {
            return null;
        }
        Nodo r = super.getRaiz();
        while (r.getrIzda() != null) {
            r = r.getrIzda();
        }
        return (T) r.getDato();
    }

// Ejercicio 5: Sumatoria de la rama derecha del árbol
    public int sumatoriaRamaDerecha() {
        if (super.getRaiz() == null || super.getRaiz().getrDrch() == null) {
            return 0;
        }
        return sumarNodos(super.getRaiz().getrDrch());
    }

    private int sumarNodos(Nodo r) {
        if (r == null) {
            return 0;
        }
        return (int) r.getDato() + sumarNodos(r.getrIzda()) + sumarNodos(r.getrDrch());
    }

    //    ejercicio 6
    private int numImpar(Nodo r) {
        if (r == null) {
            return 0;
        }

        int valor = (int) r.getDato();
        int contador;
        if (valor % 2 != 0) {
            contador = 1;
        } else {
            contador = 0;
        }

        return contador + numImpar(r.getrIzda()) + numImpar(r.getrDrch());
    }

    public int numImpar() {
        return numImpar(super.getRaiz());
    }

// Ejercicio 7: Altura del árbol
    public int altura() {
        return altura(super.getRaiz());
    }

    public int altura(Nodo r) {
        if (r == null) {
            return 0;
        }
        return 1 + Math.max(altura(r.getrIzda()), altura(r.getrDrch()));
    }

// Ejercicio 8: Recorrido por niveles
    public void recorridoPorNiveles() {
        if (super.getRaiz() == null) {
            return;
        }
        // Utilizamos las estructuras de Java directamente sin añadir imports arriba
        java.util.Queue<Nodo> cola = new java.util.LinkedList<>();
        cola.add(super.getRaiz());

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            System.out.print(actual.getDato() + " ");

            if (actual.getrIzda() != null) {
                cola.add(actual.getrIzda());
            }
            if (actual.getrDrch() != null) {
                cola.add(actual.getrDrch());
            }
        }
        System.out.println();
    }

// Ejercicio 9: Dos árboles son espejos (evaluando forma y datos)
    public boolean sonEspejos(Nodo r1, Nodo r2) {
        if (r1 == null && r2 == null) {
            return true;
        }
        if (r1 == null || r2 == null) {
            return false;
        }
        if (!r1.getDato().equals(r2.getDato())) {
            return false;
        }
        return sonEspejos(r1.getrIzda(), r2.getrDrch()) && sonEspejos(r1.getrDrch(), r2.getrIzda());
    }

    // ejercicio 10    
    public void generarEspejo() {
        super.setRaiz(generarEspejoRecursivo(super.getRaiz()));
    }

    private Nodo generarEspejoRecursivo(Nodo actual) {
        if (actual == null) {
            return null;
        }

        Nodo temp = actual.getrIzda();
        actual.setrIzda(actual.getrDrch());
        actual.setrDrch(temp);

        generarEspejoRecursivo(actual.getrIzda());
        generarEspejoRecursivo(actual.getrDrch());

        return actual;
    }

// Ejercicio 11: Árbol completamente lleno
    public boolean esCompletamenteLleno() {
        int h = altura();
        if (h == 0) {
            return true;
        }
        int hojas = contarHojas(super.getRaiz());
        int longitud = h - 1;

        return hojas == Math.pow(2, longitud);
    }

    private int contarHojas(Nodo r) {
        if (r == null) {
            return 0;
        }
        if (r.getrIzda() == null && r.getrDrch() == null) {
            return 1;
        }
        return contarHojas(r.getrIzda()) + contarHojas(r.getrDrch());
    }

// Ejercicio 12: Longitud del árbol
    public int longitud() {
        int h = altura();
        return h > 0 ? h - 1 : 0;
    }

// Ejercicio 13: Mostrar los nodos en la rama más larga
    public void mostrarRamaMasLarga() {
        ArrayList<T> caminoMayor = obtenerRamaMasLarga(super.getRaiz());
        for (T dato : caminoMayor) {
            System.out.print(dato + " ");
        }
        System.out.println();
    }

    private ArrayList<T> obtenerRamaMasLarga(Nodo r) {
        if (r == null) {
            return new ArrayList<>();
        }

        ArrayList<T> caminoIzq = obtenerRamaMasLarga(r.getrIzda());
        ArrayList<T> caminoDer = obtenerRamaMasLarga(r.getrDrch());

        ArrayList<T> caminoActual;
        if (caminoIzq.size() > caminoDer.size()) {
            caminoActual = caminoIzq;
        } else {
            caminoActual = caminoDer;
        }

        caminoActual.add(0, (T) r.getDato());
        return caminoActual;
    }

}
