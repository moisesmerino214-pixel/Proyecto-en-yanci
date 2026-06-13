/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package utilidades;

import static utilidades.Encriptar.getStringMessageDigest;

/**
 *
 * @author moise
 */
public class TestEcriptar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String clave = "laura123";
        clave= Encriptar.getStringMessageDigest(clave, Encriptar.SHA256);
        System.out.println(clave);
    }
    
}
