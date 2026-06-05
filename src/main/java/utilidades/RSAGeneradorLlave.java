/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author moise
 */
public class RSAGeneradorLlave {
    private PrivateKey clavePrivada;
    private PublicKey clavePublica;
    
    public RSAGeneradorLlave() throws Exception{
        KeyPairGenerator claveGenerada = KeyPairGenerator.getInstance("RSA");
        claveGenerada.initialize(1024);
        KeyPair par = claveGenerada.genKeyPair();
        this.clavePrivada = par.getPrivate();
        this.clavePublica = par.getPublic();
    }

    public PrivateKey getClavePrivada() {
        return clavePrivada;
    }

    public PublicKey getClavePublica() {
        return clavePublica;
    }

    public void setClavePrivada(PrivateKey clavePrivada) {
        this.clavePrivada = clavePrivada;
    }

    public void setClavePublica(PublicKey clavePublica) {
        this.clavePublica = clavePublica;
    }
    
    
    
}
