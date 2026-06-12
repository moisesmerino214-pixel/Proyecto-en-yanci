/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbol;

import java.util.ArrayList;

/**
 *
 * @author MINEDUCYT
 */

public class ArbolAVL extends ArbolBinario{
    private int fe;
    public ArbolAVL() {
        super();
        fe = 0;
    }
    public <T extends Comparable> void insertar (T dato){
        super.setRaiz(insertar(dato, super.getRaiz()));
    }
    
    private <T extends Comparable> Nodo insertar (T dato, Nodo r){
        if(r == null){
           r = new Nodo(dato);
        } else if(dato.compareTo(r.getDato()) < 0){
            Nodo izd;
           izd =  insertar(dato, r.getrIzda());
           r.setrIzda(izd);
        } else if(dato.compareTo(r.getDato()) > 0){
            Nodo drch;
            drch = insertar(dato, r.getrDrch());
            r.setrDrch(drch);
        } else{
            System.out.println("Duplicado");
        }
        return balance(dato, r);
    }
      private <T extends Comparable> Nodo balance (T dato, Nodo r){
          if(r!= null){
              if(dato.compareTo(r.getDato())>0){
                  balance(dato, r.getrDrch());
              }else if(dato.compareTo(r.getDato())<0){
                  balance(dato, r.getrIzda());
                  
              }
              fe = alturaHijo(r.getrDrch())- alturaHijo(r.getrIzda());
              switch(fe){
                  case-2:
                      if(alturaHijo(r.getrIzda().getrIzda()) >  alturaHijo(r.getrIzda().getrDrch())){
                          r = RII(r, r.getrIzda());
                      }else {
                          r = RID(r, r.getrIzda());
                      }
                      
                      break;
                  case 2:
                      if(alturaHijo(r.getrDrch().getrDrch())> alturaHijo(r.getrDrch().getrIzda())){
                          r = RDD(r,r.getrDrch());
                      }else {
                          r = RDI(r, r.getrDrch());
                      }
                      break;
                  default:
                      r = actualizarAlturaHijo(r);
              }
          }
          return r;
}
      private int alturaHijo(Nodo r){
          if(r!= null){
              return r.getAlt();
          }
          return -1;
      }
      
      private Nodo RII(Nodo r, Nodo n1){
          r.setrIzda(n1.getrDrch());
          n1.setrDrch(r);
          actualizarAlturaHijo(r);
           actualizarAlturaHijo(n1);
           return n1;
      }
      private Nodo RID(Nodo r, Nodo n1){
          Nodo n2 = n1.getrDrch();
          r.setrIzda(n2.getrDrch());
          n2.setrDrch(r);
          n1.setrDrch(n2.getrIzda());
          n2.setrIzda(n1);
           actualizarAlturaHijo(n1);
            actualizarAlturaHijo(r);
           actualizarAlturaHijo(n2);
           return n2;
      }
      private Nodo RDD(Nodo r, Nodo n1){
          r.setrDrch(n1.getrIzda());
          n1.setrIzda(r);
          actualizarAlturaHijo(r);
           actualizarAlturaHijo(n1);
           return n1;
      }
      private Nodo RDI(Nodo r, Nodo n1){
          Nodo n2 = n1.getrIzda();
          r.setrDrch(n2.getrIzda());
          n2.setrDrch(r);
          n1.setrDrch(n2.getrDrch());
          n2.setrDrch(n1);
           actualizarAlturaHijo(n1);
            actualizarAlturaHijo(r);
           actualizarAlturaHijo(n2);
           return n2;
      }
      private Nodo actualizarAlturaHijo(Nodo r){
          if (r != null){
              actualizarAlturaHijo(r.getrIzda());
              actualizarAlturaHijo(r.getrDrch());
              r.setAlt(altura(r) - 1);
          }
          return r;
      }
        public ArrayList IND(){
        ArrayList a = new ArrayList();
        return super.inOrdenIND(super.getRaiz(), a);
    }
    
    public ArrayList NID(){
        ArrayList a = new ArrayList();
        return super.preOrdenNID(super.getRaiz(), a);
    }
    
    public ArrayList IDN(){
        ArrayList a = new ArrayList();
        return super.postOrdenIND(super.getRaiz(), a);
    }
        
        
}

