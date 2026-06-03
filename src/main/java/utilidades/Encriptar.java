/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author moise
 */
public class Encriptar {
    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";
    
    public static String toHexadecimal(byte[]digest){
        String hash="";
        for(byte aux: digest){
            int b=aux & 0xff;
            if(Integer.toHexString(b).length()==1)
                hash +="0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }
    

    public static String getStringMessageDigest( String cadena, String algoritm){
        byte [] digest= null;
        byte [] buffer= cadena.getBytes();
        try{
            MessageDigest menssageDigest = MessageDigest.getInstance(algoritm);
            menssageDigest.reset();
            menssageDigest.update(buffer);
            digest=menssageDigest.digest();
        } catch(NoSuchAlgorithmException e){
            System.out.println("ERROR DE CREADO DIGEST");
        }
        return toHexadecimal(digest);
    }
}
