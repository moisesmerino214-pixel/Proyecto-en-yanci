/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.ClienteDAO;
import arbol.ArbolBBusqueda;
import arbol.Nodo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import vista.ClienteVista;

/**
 *
 * @author moise
 */
public class ClienteController implements ActionListener {
    private ClienteVista vista;
    private ClienteDAO dao;

    public ClienteController(ClienteVista vista, ClienteDAO dao) {
        this.vista = vista;
        this.dao = dao;
        
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        
        listarClientes();
    }

    private void listarClientes() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaClientes.getModel();
        modelo.setRowCount(0);
        try {
            ArbolBBusqueda<Cliente> arbol = dao.listar();
            llenarTablaRecursiva(arbol.getRaiz(), modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al listar: " + e.getMessage());
        }
    }
    
    private void llenarTablaRecursiva(Nodo nodo, DefaultTableModel modelo) {
        if (nodo != null) {
            llenarTablaRecursiva(nodo.getrIzda(), modelo);
            Cliente c = (Cliente) nodo.getDato();
            modelo.addRow(new Object[]{c.getIdCliente(), c.getNombreCliente(), c.getDui(), c.getTelefono(), c.getCorreo()});
            llenarTablaRecursiva(nodo.getrDrch(), modelo);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.btnGuardar) {
            try {
                Cliente c = new Cliente(0, vista.txtNombre.getText(), vista.txtDocumento.getText(), vista.txtTelefono.getText(), vista.txtCorreo.getText());
                dao.agregar(c);
                listarClientes();
                JOptionPane.showMessageDialog(vista, "Cliente guardado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage());
            }
        }
        else if (e.getSource() == vista.btnModificar) {
            int fila = vista.tablaClientes.getSelectedRow();
            if (fila >= 0) {
                try {
                    int id = (int) vista.tablaClientes.getValueAt(fila, 0);
                    Cliente c = new Cliente(id, vista.txtNombre.getText(), vista.txtDocumento.getText(), 
                                            vista.txtTelefono.getText(), vista.txtCorreo.getText());
                    dao.modificar(c);
                    listarClientes();
                    JOptionPane.showMessageDialog(vista, "Cliente modificado");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vista, "Error al modificar: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Seleccione un cliente de la tabla");
            }
        }
        else if (e.getSource() == vista.btnEliminar) {
            int fila = vista.tablaClientes.getSelectedRow();
            if (fila >= 0) {
                int id = (int) vista.tablaClientes.getValueAt(fila, 0);
                try {
                    dao.eliminar(id);
                    listarClientes();
                    JOptionPane.showMessageDialog(vista, "Cliente eliminado");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Seleccione un cliente de la tabla");
            }
        }
        else if (e.getSource() == vista.btnLimpiar) {
            vista.txtNombre.setText("");
            vista.txtDocumento.setText("");
            vista.txtTelefono.setText("");
            vista.txtCorreo.setText("");
        }
        else if (e.getSource() == vista.btnSalir) {
            vista.dispose();
        }
    }
}
