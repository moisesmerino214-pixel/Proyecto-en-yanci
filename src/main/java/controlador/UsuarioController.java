/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controladorVendedor.VentasController;
import interfaz.UsuarioInterfaz;
import arbol.ArbolBBusqueda;
import arbol.Nodo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vistaVendedor.VentasView;
import vista.MenuView;
import vista.VistaInicio;

/**
 *
 * @author moise
 */
public class UsuarioController implements ActionListener{
    private VistaInicio vista;
    private UsuarioInterfaz dao;
    private ArbolBBusqueda<Usuario> arbolUsuarios; 

    public UsuarioController(VistaInicio vista, UsuarioInterfaz dao) {
        this.vista = vista;
        this.dao = dao;
        this.vista.btnIniciarSesion.addActionListener(this);
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        try {
            arbolUsuarios = dao.listar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar usuarios: " + e.getMessage());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnIniciarSesion) {
            String usuarioIngresado = vista.txtUsuario.getText();
            String contrasenaIngresada = new String(vista.txtContrasena.getPassword());

            if (usuarioIngresado.trim().isEmpty() || contrasenaIngresada.trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, llene todos los campos.");
                return;
            }

            Usuario usuarioBuscado = new Usuario();
            usuarioBuscado.setNombreUsuario(usuarioIngresado);
            Nodo nodoEncontrado = arbolUsuarios.buscar(usuarioBuscado);

            if (nodoEncontrado != null) {
                Usuario usuarioEncontrado = (Usuario) nodoEncontrado.getDato();
                String contrasenaEncriptadaIngresada = utilidades.Encriptar.getStringMessageDigest(contrasenaIngresada, utilidades.Encriptar.SHA256);
                if (usuarioEncontrado.getContrasena().equals(contrasenaEncriptadaIngresada)) {
                    JOptionPane.showMessageDialog(vista, "¡Inicio de sesion exitoso!");

                    int rol = usuarioEncontrado.getIdRol();

                    if (rol == 1) {
                        MenuView menu = new MenuView();
                        new MenuController(menu);
                        menu.setVisible(true);
                    } else if (rol == 2) {
                        VentasView ventas = new VentasView();
                        new VentasController(ventas, usuarioEncontrado);
                        ventas.setVisible(true);
                    }

                    vista.dispose();
                } else {
                    JOptionPane.showMessageDialog(vista, "Contraseña incorrecta.");
                }
            } else {
                JOptionPane.showMessageDialog(vista, "El usuario ingresado no existe.");
            }
        }
    }
}