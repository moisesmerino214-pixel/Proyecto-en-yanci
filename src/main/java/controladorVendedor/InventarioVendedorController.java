/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorVendedor;

import DAOVendedor.InventarioVendedorDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vistaVendedor.InventarioVendedor;
import vistaVendedor.VentasView;

/**
 *
 * @author moise
 */
public class InventarioVendedorController implements ActionListener {
    private InventarioVendedor vista;
    private InventarioVendedorDAO dao;
    private VentasView ventasPrevias;
    
    public InventarioVendedorController(InventarioVendedor vista, VentasView ventasPrevias) {
        this.vista = vista;
        this.ventasPrevias = ventasPrevias;
        this.dao = new InventarioVendedorDAO();
        
        vista.btnBusacr.addActionListener(this);
        vista.btnRegresar.addActionListener(this);

        cargarTodo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBusacr) buscar();
        if (e.getSource() == vista.btnRegresar)  regresar();
    }
    
    private void cargarTodo() {
        try {
            dao.cargarInventario(vista.tablaInventario1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar inventario:\n" + ex.getMessage());
        }
    }
    
    private void buscar() {
        String criterio = vista.txtBuscar.getText().trim();
        try {
            if (criterio.isEmpty()) {
                dao.cargarInventario(vista.tablaInventario1);
            } else {
                dao.buscarInventario(vista.tablaInventario1, criterio);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar:\n" + ex.getMessage());
        }
    }
    
    private void regresar() {
        java.awt.Window ventanaActual = javax.swing.SwingUtilities.getWindowAncestor(vista);
        if (ventanaActual != null) {
            ventanaActual.dispose();
        }
        if (ventasPrevias != null) {
            ventasPrevias.setVisible(true);
        }
    }
}
    

