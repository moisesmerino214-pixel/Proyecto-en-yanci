/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.EmpleadoDAO;
import arbol.ArbolBBusqueda;
import arbol.Nodo;
import interfaz.EmpleadoInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Empleado;
import vista.EmpleadoVista;

/**
 *
 * @author moise
 */
public class EmpeladoController implements ActionListener {
    private final EmpleadoVista vista;
    private final EmpleadoInterfaz dao;
    private ArbolBBusqueda<Empleado> arbol;

    public EmpeladoController(EmpleadoVista vista) {
        this.vista = vista;
        this.dao = new EmpleadoDAO();
        this.arbol = new ArbolBBusqueda<>();

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnGuardar1.addActionListener(this); 
        this.vista.radioVendedor.addActionListener(this);

        this.vista.tablaEmpleado.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = vista.tablaEmpleado.getSelectedRow();
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
        else if (src == vista.radioVendedor) gestionarDinamicaCargo();
    }

    private void cargarTabla() {
        try {
            arbol = dao.listar();
            DefaultTableModel modelo = (DefaultTableModel) vista.tablaEmpleado.getModel();
            modelo.setRowCount(0);
            recorrerInOrden(arbol.getRaiz(), modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tabla:\n" + ex.getMessage());
        }
    }

    private void recorrerInOrden(Nodo<Empleado> nodo, DefaultTableModel modelo) {
        if (nodo == null) return;
        recorrerInOrden(nodo.getrIzda(), modelo);
        Empleado emp = nodo.getDato();
        modelo.addRow(new Object[]{
            emp.getIdEmpleado(),
            emp.getNombre(),
            emp.getTelefono(),
            emp.getCorreo(),
            emp.getCargo(),
            emp.getNombreUsuario()
        });
        recorrerInOrden(nodo.getrDrch(), modelo);
    }

    private void cargarDatosEnFormulario(int fila) {
        try {
            vista.txtNombre.setText(String.valueOf(vista.tablaEmpleado.getValueAt(fila, 1)));
            vista.txtTelefono.setText(String.valueOf(vista.tablaEmpleado.getValueAt(fila, 2)));
            vista.txtCorreo.setText(String.valueOf(vista.tablaEmpleado.getValueAt(fila, 3)));
            vista.radioVendedor.setSelected(true);
            vista.txtUsuario.setText(String.valueOf(vista.tablaEmpleado.getValueAt(fila, 5)));
            vista.txtContraseña.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al seleccionar registro:\n" + ex.getMessage());
        }
    }

    private void guardar() {
        if (!validarCampos()) {
            return;
        }
        try {
            String usuario = vista.txtUsuario.getText().trim();
            String contrasena = vista.txtContraseña.getText().trim();
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Usuario y contraseña son obligatorios.");
                return;
            }
            dao.agregar(construirDesdeFormulario(0), usuario, contrasena);
            JOptionPane.showMessageDialog(vista, "Vendedor guardado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar:\n" + ex.getMessage());
        }
    }

    private void modificar() {
        int fila = vista.tablaEmpleado.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un empleado de la tabla.");
            return;
        }
        if (!validarCampos()) return;
        try {
           int id = (int) vista.tablaEmpleado.getValueAt(fila, 0);
            String nuevaClave = vista.txtContraseña.getText().trim();
            dao.modificar(construirDesdeFormulario(id), nuevaClave);
            JOptionPane.showMessageDialog(vista, "Vendedor modificado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al modificar:\n" + ex.getMessage());
        }
    }

    private void eliminar() {
        int fila = vista.tablaEmpleado.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un empleado para eliminar.");
            return;
        }
        int id = (int) vista.tablaEmpleado.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(vista, "¿Desea eliminar al empleado con ID " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            dao.eliminar(id);
            JOptionPane.showMessageDialog(vista, "Empleado eliminado con éxito.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar:\n" + ex.getMessage());
        }
    }

    private void gestionarDinamicaCargo() {
        if (!vista.radioVendedor.isSelected()) {
            vista.radioVendedor.setSelected(false);
        }
    }

    private Empleado construirDesdeFormulario(int id) {
        Empleado emp = new Empleado();
        emp.setIdEmpleado(id);
        emp.setNombre(vista.txtNombre.getText().trim());
        emp.setTelefono(vista.txtTelefono.getText().trim());
        emp.setCorreo(vista.txtCorreo.getText().trim());
        emp.setCargo(vista.radioVendedor.isSelected() ? "Vendedor" : "Regular");
        return emp;
    }

    private boolean validarCampos() {
        if (vista.txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El campo Nombre es obligatorio."); return false; }
        if (vista.txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El campo Teléfono es obligatorio."); return false; }
        if (vista.txtCorreo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El campo Correo es obligatorio."); return false; }
        return true;
    }

    private void limpiar() {
        vista.txtNombre.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
        vista.txtUsuario.setText("");
        vista.txtContraseña.setText("");
        vista.radioVendedor.setSelected(false);
        vista.tablaEmpleado.clearSelection();
    }
}
