/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.MasVendidosDAO;
import arbol.ArbolBBusqueda;
import arbol.Nodo;
import java.awt.Window;
import modelo.ProductoMasVendido;
import vista.MasVendidosView;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author moise
 */
public class MasVendidosController implements ActionListener {
    private final MasVendidosView vista;
    private final MasVendidosDAO dao;
    private ArbolBBusqueda<ProductoMasVendido> arbol;

    public MasVendidosController(MasVendidosView vista) {
        this.vista = vista;
        this.dao = new MasVendidosDAO();
        this.arbol = new ArbolBBusqueda<>();
        this.vista.btnSalir.addActionListener(this);
        
        cargarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == vista.btnSalir) {
            Window win = SwingUtilities.getWindowAncestor(vista);
            if (win != null) win.dispose();
        }
    }

    private void cargarTabla() {
        try {
            arbol = dao.listarMasVendidos();
            DefaultTableModel modelo = (DefaultTableModel) vista.tablaMasVendidos.getModel();
            modelo.setRowCount(0);
            recorrerInOrden(arbol.getRaiz(), modelo);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar reporte:\n" + ex.getMessage());
        }
    }

    private void recorrerInOrden(Nodo<ProductoMasVendido> nodo, DefaultTableModel modelo) {
        if (nodo == null) return;
        recorrerInOrden((Nodo<ProductoMasVendido>) nodo.getrIzda(), modelo);
        
        ProductoMasVendido p = (ProductoMasVendido) nodo.getDato();
        modelo.addRow(new Object[]{
            p.getNombre(),
            "Calzado",
            p.getPrecio(),
            p.getParesVendidos()
        });
        recorrerInOrden((Nodo<ProductoMasVendido>) nodo.getrDrch(), modelo);
    }
}