/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorVendedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vistaVendedor.InventarioVendedor;
import vistaVendedor.MenuVendedorView;
import vistaVendedor.VentasView;

/**
 *
 * @author moise
 */
public class MenuVendedorController implements ActionListener {
    private MenuVendedorView vista;

    public MenuVendedorController(MenuVendedorView vista) {
        this.vista = vista;
        this.vista.btnVenta.addActionListener(this);
        this.vista.btnInventario.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);

        this.vista.btnVenta.setActionCommand("Ventas");
        this.vista.btnInventario.setActionCommand("Inventario");
        this.vista.btnSalir.setActionCommand("Salir");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String menu = e.getActionCommand();

        switch (menu) {
            case "Ventas":
                VentasView ventas = new VentasView();
                new VentasController(ventas, vista);
                ventas.setVisible(true);
                vista.setVisible(false);
                break;

            case "Inventario":
                InventarioVendedor inventario = new InventarioVendedor();
                new InventarioVendedorController(inventario, vista);
                javax.swing.JFrame frameInv = new javax.swing.JFrame("Gestión de Inventario");
                frameInv.setContentPane(inventario);
                frameInv.pack();
                frameInv.setLocationRelativeTo(null);
                frameInv.setVisible(true);
                vista.setVisible(false);
                break;
                
            case "Salir":
                vista.dispose();
                break;

            default:
                System.out.println("Acción no reconocida");
                break;
        }
    }
}