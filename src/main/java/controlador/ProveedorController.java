/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.ProveedorDAO;
import arbol.ArbolBBusqueda;
import arbol.Nodo;
import interfaz.ProveedorInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Proveedor;
import vista.ProveedoresView;

/**
 *
 * @author moise
 */
public class ProveedorController implements ActionListener{
    private final ProveedoresView vista;
    private final ProveedorInterfaz dao;
    private ArbolBBusqueda<Proveedor> arbol;

    public ProveedorController(ProveedoresView vista) {
        this.vista = vista;
        this.dao = new ProveedorDAO();
        this.arbol = new ArbolBBusqueda<>();

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnGuardar1.addActionListener(this);

        this.vista.tablaproveedores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = vista.tablaproveedores.getSelectedRow();
                if (fila >= 0) cargarDatosEnFormulario(fila);
            }
        });

        cargarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == vista.btnGuardar) guardar();
        else if (src == vista.btnModificar) modificar();
        else if (src == vista.btnEliminar) eliminar();
        else if (src == vista.btnLimpiar) limpiar();
        else if (src == vista.btnGuardar1) vista.dispose();
    }

    private void cargarTabla() {
        try {
            arbol = dao.listar();
            DefaultTableModel modelo = (DefaultTableModel) vista.tablaproveedores.getModel();
            modelo.setRowCount(0);
            recorrerInOrden(arbol.getRaiz(), modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar la tabla de proveedores:\n" + ex.getMessage());
        }
    }

    private void recorrerInOrden(Nodo<Proveedor> nodo, DefaultTableModel modelo) {
        if (nodo == null) return;
        recorrerInOrden(nodo.getrIzda(), modelo);
        
        Proveedor prov = nodo.getDato();
        modelo.addRow(new Object[]{
            prov.getIdProveedor(),
            prov.getNombreEmpresa(),
            prov.getNombreProveedor(),
            prov.getTelefono(),
            prov.getCorreo()
        });
        
        recorrerInOrden(nodo.getrDrch(), modelo);
    }

    private void cargarDatosEnFormulario(int fila) {
        try {
            vista.txtNombreEmpresa.setText(String.valueOf(vista.tablaproveedores.getValueAt(fila, 1)));
            vista.txtNombreProovedor.setText(String.valueOf(vista.tablaproveedores.getValueAt(fila, 2)));
            vista.txtTelefono.setText(String.valueOf(vista.tablaproveedores.getValueAt(fila, 3)));
            vista.txtCorreo.setText(String.valueOf(vista.tablaproveedores.getValueAt(fila, 4)));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al seleccionar proveedor:\n" + ex.getMessage());
        }
    }

    private void guardar() {
        if (!validarCampos()) return;
        try {
            dao.agregar(construirDesdeFormulario(0));
            JOptionPane.showMessageDialog(vista, "Proveedor guardado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar:\n" + ex.getMessage());
        }
    }

    private void modificar() {
        int fila = vista.tablaproveedores.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un proveedor de la tabla.");
            return;
        }
        if (!validarCampos()) return;
        try {
            int id = (int) vista.tablaproveedores.getValueAt(fila, 0);
            dao.modificar(construirDesdeFormulario(id));
            JOptionPane.showMessageDialog(vista, "Proveedor modificado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al modificar:\n" + ex.getMessage());
        }
    }

    private void eliminar() {
        int fila = vista.tablaproveedores.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un proveedor para eliminar.");
            return;
        }
        int id = (int) vista.tablaproveedores.getValueAt(fila, 0);
        String empresa = String.valueOf(vista.tablaproveedores.getValueAt(fila, 1));
        
        if (JOptionPane.showConfirmDialog(vista, "¿Desea eliminar al proveedor \"" + empresa + "\"? (ID: " + id + ")",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            dao.eliminar(id);
            JOptionPane.showMessageDialog(vista, "Proveedor eliminado con éxito.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar:\n" + ex.getMessage());
        }
    }

    private Proveedor construirDesdeFormulario(int id) {
        Proveedor prov = new Proveedor();
        prov.setIdProveedor(id);
        prov.setNombreEmpresa(vista.txtNombreEmpresa.getText().trim());
        prov.setNombreProveedor(vista.txtNombreProovedor.getText().trim());
        prov.setTelefono(vista.txtTelefono.getText().trim());
        prov.setCorreo(vista.txtCorreo.getText().trim());
        return prov;
    }

    private boolean validarCampos() {
        if (vista.txtNombreEmpresa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre de la empresa es obligatorio."); return false; }
        if (vista.txtNombreProovedor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre del proveedor es obligatorio."); return false; }
        if (vista.txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El teléfono es obligatorio."); return false; }
        if (vista.txtCorreo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El correo electrónico es obligatorio."); return false; }
        return true;
    }

    private void limpiar() {
        vista.txtNombreEmpresa.setText("");
        vista.txtNombreProovedor.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
        vista.tablaproveedores.clearSelection();
    }
}
