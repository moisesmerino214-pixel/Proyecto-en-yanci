/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.GestionVentasDAO;
import arbol.ArbolBBusqueda;
import interfaz.GestionVentasInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modeloVendedor.DetalleVenta;
import modeloVendedor.Ventas;
import vista.VentasView;

/**
 *
 * @author moise
 */
public class GestionVentasController implements ActionListener {
    private VentasView view;
    private GestionVentasInterfaz dao;
    private ArbolBBusqueda<DetalleVenta> arbolDetalle = new ArbolBBusqueda<>();
    
    public GestionVentasController(VentasView view) {
        this.view = view;
        this.dao = new GestionVentasDAO();

        view.btnBuscar.addActionListener(this);
        view.btnAnular.addActionListener(this);
        view.btnSalir.addActionListener(this);

        view.tablaVentas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.tablaVentas.getSelectedRow() >= 0) {
                int idVenta = (int) view.tablaVentas.getValueAt(
                    view.tablaVentas.getSelectedRow(), 0);
                try {
                    cargarDetalle(idVenta);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage());
                }
            }
        });

        cargarTodo();
    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btnBuscar) buscar();
        if (e.getSource() == view.btnAnular) anular();
        if (e.getSource() == view.btnSalir)  cerrar();
    }

    private void cargarTodo() {
        try {
            ArbolBBusqueda<Ventas> arbol = dao.listarVentas();
            DefaultTableModel modelo = (DefaultTableModel) view.tablaVentas.getModel();
            modelo.setRowCount(0);
            for (Object obj : arbol.IND()) {
                Ventas v = (Ventas) obj;
                modelo.addRow(new Object[]{
                    v.getIdVenta(),
                    v.getNombreCliente(),  
                    v.getNombreVendedor(),
                    v.getFecha(),
                    String.format("$%.2f", v.getTotal())
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage());
        }
    }
    
    private void cargarDetalle(int idVenta) throws Exception {
        arbolDetalle = dao.listarDetalle(idVenta);
        DefaultTableModel modelo = (DefaultTableModel) view.tablaDetalle.getModel();
        modelo.setRowCount(0);
        for (Object obj : arbolDetalle.IND()) {
            DetalleVenta d = (DetalleVenta) obj;
            modelo.addRow(new Object[]{
                d.getIdDetalleVenta(),
                d.getProducto(),
                d.getColor(),
                d.getTalla(),
                d.getCantidad(),
                String.format("$%.2f", d.getPrecioUnitario()),
                String.format("$%.2f", d.getSubtotal())
            });
        }
    }
    
    private void buscar() {
        String criterio = view.txtBuscar.getText().trim();
        String filtro = view.comboFiltrar.getSelectedItem().toString();
        try {
            ArbolBBusqueda<Ventas> arbol;
            if (criterio.isEmpty()) {
                arbol = dao.listarVentas();
            } else {
                switch (filtro) {
                    case "Cliente"  -> arbol = dao.buscarPorCliente(criterio);
                    case "Vendedor" -> arbol = dao.buscarPorVendedor(criterio);
                    default         -> arbol = dao.buscarPorFecha(criterio);
                }
            }
            DefaultTableModel modelo = (DefaultTableModel) view.tablaVentas.getModel();
            modelo.setRowCount(0);
            for (Object obj : arbol.IND()) {
                Ventas v = (Ventas) obj;
                modelo.addRow(new Object[]{
                    v.getIdVenta(),
                    v.getNombreCliente(),
                    v.getNombreVendedor(),
                    v.getFecha(),
                    String.format("$%.2f", v.getTotal())
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage());
        }
    }
    
    private void anular() {
        int fila = view.tablaVentas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione una venta."); return;
        }
        int idVenta = (int) view.tablaVentas.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(null, "¿Anular venta #" + idVenta + "? Se restaurará el stock.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            dao.anularVenta(idVenta);
            JOptionPane.showMessageDialog(null, "Venta #" + idVenta + " anulada.");
            cargarTodo();
            ((DefaultTableModel) view.tablaDetalle.getModel()).setRowCount(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error:\n" + ex.getMessage());
        }
    }

    private void cerrar() {
        javax.swing.SwingUtilities.getWindowAncestor(view).dispose();
    }
}

