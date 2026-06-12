/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import vista.InventarioView;
import DAO.InventarioDAO;
import arbol.ArbolAVL;
import modelo.Inventario;
import arbol.Nodo;
import java.text.DecimalFormat;


/**
 *
 * @author moise
 */
public class InventarioController implements ActionListener {
    private final InventarioView vista;
    private final InventarioDAO  dao;
    private ArbolAVL arbol;
    private final DecimalFormat df = new DecimalFormat("0.00");

    public InventarioController(InventarioView vista, InventarioDAO dao) {
        this.vista = vista;
        this.dao   = dao;
        this.arbol = new ArbolAVL();

        try {
            dao.cargarProductos(vista.comboProducto);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                "Error al cargar productos:\n" + ex.getMessage());
        }

        vista.btnAgregar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnBusacr.addActionListener(this);
        vista.btnSalir.addActionListener(this);

        vista.tablaInventario.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = vista.tablaInventario.getSelectedRow();
                if (fila >= 0) cargarDatosEnFormulario(fila);
            }
        });

        cargarTabla();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if      (src == vista.btnAgregar)   agregar();
        else if (src == vista.btnModificar) modificar();
        else if (src == vista.btnEliminar)  eliminar();
        else if (src == vista.btnBusacr)    buscar();
        else if (src == vista.btnSalir)     vista.dispose();
    }
    
    private void cargarTabla() {
        try {
            arbol = dao.listar();
            DefaultTableModel modelo = (DefaultTableModel) vista.tablaInventario.getModel();
            modelo.setRowCount(0);
            recorrerInOrden(arbol.getRaiz(), modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                "Error al cargar inventario:\n" + ex.getMessage());
        }
    }
    
    private void recorrerInOrden(Nodo<Inventario> nodo, DefaultTableModel modelo) {
        if (nodo == null) return;
        recorrerInOrden((Nodo<Inventario>) nodo.getrIzda(), modelo);
        Inventario inv = (Inventario) nodo.getDato();
        DecimalFormat df = new DecimalFormat("0.00");
        modelo.addRow(new Object[]{
            inv.getIdInventario(),  
            inv.getNombreProducto(),    
            inv.getTalla(),         
            inv.getColor(),     
            inv.getStock(),         
            inv.getStock(),       
            df.format(inv.getPrecio())
        });
        recorrerInOrden((Nodo<Inventario>) nodo.getrDrch(), modelo);
    }
    
    private void cargarDatosEnFormulario(int fila) {
        try {
            vista.txtPrecio.setText(String.valueOf(vista.tablaInventario.getValueAt(fila, 6)));
            vista.txtCantidad.setText(String.valueOf(vista.tablaInventario.getValueAt(fila, 4)));
            vista.txtTalla.setText(String.valueOf(vista.tablaInventario.getValueAt(fila, 2)));
            vista.txtColor.setText(String.valueOf(vista.tablaInventario.getValueAt(fila, 3)));
            
            String nombreTabla = (String) vista.tablaInventario.getValueAt(fila, 1);
            for (int i = 0; i < vista.comboProducto.getItemCount(); i++) {
                if (vista.comboProducto.getItemAt(i).contains(nombreTabla)) {
                    vista.comboProducto.setSelectedIndex(i);
                    break;
                }
            }
        } catch (Exception ex) {
        }
    }
    
    private void agregar() {
        if (!validarCampos()) return;
        try {
            Inventario inv = construirInventarioDesdeFormulario(0);
            dao.registrar(inv);
            JOptionPane.showMessageDialog(vista, "Inventario registrado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar:\n" + ex.getMessage());
        }
    }
    
    private void modificar() {
        int fila = vista.tablaInventario.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un registro para modificar.");
            return;
        }
        if (!validarCampos()) return;
        try {
            int id = (int) vista.tablaInventario.getValueAt(fila, 0);
            Inventario inv = construirInventarioDesdeFormulario(id);
            dao.modificar(inv);
            JOptionPane.showMessageDialog(vista, "Inventario modificado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al modificar:\n" + ex.getMessage());
        }
    }
    
    private void eliminar() {
        int fila = vista.tablaInventario.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un registro para eliminar.");
            return;
        }
        int id = (int) vista.tablaInventario.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(vista,
            "¿Eliminar el registro con ID " + id + "?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            dao.eliminar(id);
            JOptionPane.showMessageDialog(vista, "Registro eliminado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar:\n" + ex.getMessage());
        }
    }

    private void buscar() {
        String texto = vista.txtBuscar.getText().trim().toLowerCase();
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaInventario.getModel();
        modelo.setRowCount(0);
        buscarPorTextoInOrden(arbol.getRaiz(), texto, modelo);
        
        if (modelo.getRowCount() == 0 && !texto.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Sin resultados para: \"" + texto + "\"");
            cargarTabla();
        }
    }

    private void buscarPorTextoInOrden(Nodo<Inventario> nodo, String texto, DefaultTableModel modelo) {
        if (nodo == null) return;
        buscarPorTextoInOrden((Nodo<Inventario>) nodo.getrIzda(), texto, modelo);
        Inventario inv = (Inventario) nodo.getDato();
        if (inv.getNombreProducto().toLowerCase().contains(texto) || inv.getColor().toLowerCase().contains(texto) || inv.getTalla().contains(texto)) {
            modelo.addRow(new Object[]{
                inv.getIdInventario(), inv.getNombreProducto(), inv.getTalla(),
                inv.getColor(), inv.getStock(), inv.getStock(), df.format(inv.getPrecio()) 
            });
        }
        buscarPorTextoInOrden((Nodo<Inventario>) nodo.getrDrch(), texto, modelo);
    }

    private Inventario construirInventarioDesdeFormulario(int id) {
        String seleccion = vista.comboProducto.getSelectedItem().toString();
        String[] partes = seleccion.split(" - ");
        int idProducto = Integer.parseInt(partes[0].trim());
        String nombreProducto = partes[1].trim();

        return new Inventario(
            id,
            idProducto,
            nombreProducto,
            vista.txtColor.getText().trim(),
            vista.txtTalla.getText().trim(),
            Integer.parseInt(vista.txtCantidad.getText().trim()),
            Double.parseDouble(vista.txtPrecio.getText().trim())
        );
    }

    private boolean validarCampos() {
        if (vista.comboProducto.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto.");
            return false;
        }
        if (vista.txtColor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el color.");
            return false;
        }
        if (vista.txtTalla.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese la talla.");
            return false;
        }
        if (vista.txtCantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese la cantidad.");
            return false;
        }
        if (vista.txtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el precio.");
            return false;
        }
        try {
            int c = Integer.parseInt(vista.txtCantidad.getText().trim());
            if (c <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Cantidad debe ser un entero positivo.");
            return false;
        }
        try {
            double p = Double.parseDouble(vista.txtPrecio.getText().trim());
            if (p <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Precio debe ser un número positivo.");
            return false;
        }
        try {
        Double.parseDouble(vista.txtTalla.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "La talla debe ser un número válido.");
            return false;
        }
        return true;
    }

    private void limpiar() {
        vista.txtColor.setText("");
        vista.txtTalla.setText("");
        vista.txtCantidad.setText("");
        vista.txtPrecio.setText("");
        vista.txtBuscar.setText("");
        if (vista.comboProducto.getItemCount() > 0)
            vista.comboProducto.setSelectedIndex(0);
    }
}