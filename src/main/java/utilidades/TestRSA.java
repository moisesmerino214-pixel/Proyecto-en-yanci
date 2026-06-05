/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package utilidades;

import java.util.Base64;

/**
 *
 * @author moise
 */
public class TestRSA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        RSAGeneradorLlave generadorClves = new RSAGeneradorLlave();
        
        System.out.println(
        Base64.getEncoder().encodeToString(
        generadorClves.getClavePublica().getEncoded()));

        System.out.println(
        Base64.getEncoder().encodeToString(
        generadorClves.getClavePrivada().getEncoded()));
    }
    
}
