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
import vistaVendedor.MenuVendedorView;

/**
 *
 * @author moise
 */
public class InventarioVendedorController implements ActionListener {
    private InventarioVendedor vista;
    private InventarioVendedorDAO dao;
    private MenuVendedorView menu;

    public InventarioVendedorController(InventarioVendedor vista, MenuVendedorView menu) {
        this.vista = vista;
        this.dao = new InventarioVendedorDAO();
        this.menu = menu;
        
        vista.btnBusacr.addActionListener(this);
        vista.btnSalir.addActionListener(this);

        cargarTodo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBusacr) buscar();
        if (e.getSource() == vista.btnSalir)  cerrar();
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
    
    private void cerrar() {
        javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();
        menu.setVisible(true);
    }
}
    

