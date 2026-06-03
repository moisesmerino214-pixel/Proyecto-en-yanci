package controlador;

import DAO.ProductoDAO;
import arbol.ArbolBBusqueda;
import arbol.Nodo;
import interfaz.ProductoInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import vista.ViewProducto;

public class ProductoController implements ActionListener {
private final ViewProducto       vista;
    private final ProductoInterfaz   dao;
    private ArbolBBusqueda<Producto> arbol;

    public ProductoController(ViewProducto vista) {
        this.vista = vista;
        this.dao   = new ProductoDAO();
        this.arbol = new ArbolBBusqueda<>();

        try {
            dao.cargarMarcas(vista.cmbMarca);
            dao.cargarColores(vista.cmbcolor);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar datos:\n" + ex.getMessage());
        }

        vista.btnAgregar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnVerTodo.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);
        vista.btnSalir.addActionListener(this);

        vista.jTable2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = vista.jTable2.getSelectedRow();
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
        else if (src == vista.btnBuscar)    buscar();
        else if (src == vista.btnVerTodo)   cargarTabla();
        else if (src == vista.btnLimpiar)   limpiar();
        else if (src == vista.btnSalir)     vista.dispose();
    }

    private void cargarTabla() {
        try {
            arbol = dao.listar();
            DefaultTableModel modelo = (DefaultTableModel) vista.jTable2.getModel();
            modelo.setRowCount(0);
            recorrerInOrden(arbol.getRaiz(), modelo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar:\n" + ex.getMessage());
        }
    }

    private void recorrerInOrden(Nodo<Producto> nodo, DefaultTableModel modelo) {
        if (nodo == null) return;
        recorrerInOrden((Nodo<Producto>) nodo.getrIzda(), modelo);
        Producto p = (Producto) nodo.getDato();
        modelo.addRow(new Object[]{
            p.getIdProducto(),  
            p.getModelo(),      
            p.getTalla(),       
            p.getStock(),       
            p.getMarca(),       
            p.getColor(),       
            p.getPrecio()       
        });
        recorrerInOrden((Nodo<Producto>) nodo.getrDrch(), modelo);
    }

    private void cargarDatosEnFormulario(int fila) {
        try {
            vista.txtModelo.setText(String.valueOf(vista.jTable2.getValueAt(fila, 1)));
            vista.txtTalla.setText(String.valueOf(vista.jTable2.getValueAt(fila, 2)));
            vista.txtStock.setText(String.valueOf(vista.jTable2.getValueAt(fila, 3)));
            vista.txtPrecio.setText(String.valueOf(vista.jTable2.getValueAt(fila, 6)));

            String marca = String.valueOf(vista.jTable2.getValueAt(fila, 4));
            for (int i = 0; i < vista.cmbMarca.getItemCount(); i++) {
                if (vista.cmbMarca.getItemAt(i).contains("(" + marca + ")")) {
                    vista.cmbMarca.setSelectedIndex(i);
                    break;
                }
            }

            String color = String.valueOf(vista.jTable2.getValueAt(fila, 5));
            for (int i = 0; i < vista.cmbcolor.getItemCount(); i++) {
                if (vista.cmbcolor.getItemAt(i).equals(color)) {
                    vista.cmbcolor.setSelectedIndex(i);
                    break;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar formulario:\n" + ex.getMessage());
        }
    }

    private void agregar() {
        if (!validarCampos()) return;
        try {
            Producto p = construirDesdeFormulario(0, 0);
            dao.agregar(p);
            JOptionPane.showMessageDialog(vista, "Producto agregado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar:\n" + ex.getMessage());
        }
    }

    private void modificar() {
        int fila = vista.jTable2.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un registro para modificar.");
            return;
        }
        if (!validarCampos()) return;
        try {
            int idInventario = (int) vista.jTable2.getValueAt(fila, 0);
            Producto clave = new Producto();
            clave.setIdProducto(idInventario);
            Nodo<Producto> nodo = arbol.buscar(clave);
            
            if (nodo == null) {
                JOptionPane.showMessageDialog(vista, "No se encontró el producto en la estructura local del árbol.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int idProductoBD = ((Producto) nodo.getDato()).getIdProductoBD();
            Producto p = construirDesdeFormulario(idInventario, idProductoBD);
            dao.modificar(p);
            
            JOptionPane.showMessageDialog(vista, "Producto modificado correctamente.");
            limpiar();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al modificar:\n" + ex.getMessage());
        }
    }

    private void eliminar() {
        int fila = vista.jTable2.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un registro para eliminar.");
            return;
        }
        int id = (int) vista.jTable2.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(vista, "¿Eliminar registro ID " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
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
        String texto = vista.jTextField4.getText().trim();
        if (texto.isEmpty()) { cargarTabla(); return; }

        DefaultTableModel modelo = (DefaultTableModel) vista.jTable2.getModel();
        modelo.setRowCount(0);

        try {
            Producto clave = new Producto();
            clave.setIdProducto(Integer.parseInt(texto));
            Nodo nodo = arbol.buscar(clave);
            if (nodo != null) {
                Producto p = (Producto) nodo.getDato();
                modelo.addRow(new Object[]{
                    p.getIdProducto(), p.getModelo(), p.getTalla(),
                    p.getStock(), p.getMarca(), p.getColor(), p.getPrecio()
                });
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró ID: " + texto);
            }
        } catch (NumberFormatException ex) {
            buscarPorTexto(arbol.getRaiz(), texto.toLowerCase(), modelo);
            if (modelo.getRowCount() == 0)
                JOptionPane.showMessageDialog(vista, "Sin resultados para: \"" + texto + "\"");
        }
    }

    private void buscarPorTexto(Nodo<Producto> nodo, String texto, DefaultTableModel modelo) {
    if (nodo == null) return;
    buscarPorTexto((Nodo<Producto>) nodo.getrIzda(), texto, modelo);
    Producto p = (nodo != null) ? (Producto) nodo.getDato() : null;
    if (p != null && (p.getModelo().toLowerCase().contains(texto)
            || p.getMarca().toLowerCase().contains(texto)
            || p.getColor().toLowerCase().contains(texto))) {
        modelo.addRow(new Object[]{
            p.getIdProducto(), p.getModelo(), p.getTalla(),
            p.getStock(), p.getMarca(), p.getColor(), p.getPrecio()
        });
    }
    buscarPorTexto((Nodo<Producto>) nodo.getrDrch(), texto, modelo);
}

    private Producto construirDesdeFormulario(int idInventario, int idProductoBD) {
        String item = vista.cmbMarca.getSelectedItem().toString();
        int idProdCombo = Integer.parseInt(item.split(" - ")[0].trim());

        Producto p = new Producto();
        p.setIdProducto(idInventario);
        p.setIdProductoBD(idProductoBD != 0 ? idProductoBD : idProdCombo);
        p.setModelo(vista.txtModelo.getText().trim());
        p.setTalla(vista.txtTalla.getText().trim());
        p.setStock(Integer.parseInt(vista.txtStock.getText().trim()));
        
        if (item.contains("(") && item.contains(")")) {
            String marcaLimpia = item.substring(item.indexOf("(") + 1, item.lastIndexOf(")"));
            p.setMarca(marcaLimpia);
        } else {
            p.setMarca(item);
        }
        
        p.setColor(vista.cmbcolor.getSelectedItem().toString());
        p.setPrecio(Double.parseDouble(vista.txtPrecio.getText().trim()));
        return p;
    }

    private boolean validarCampos() {
        if (vista.txtModelo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el modelo."); return false; }
        if (vista.txtTalla.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese la talla."); return false; }
        try {
            if (Integer.parseInt(vista.txtStock.getText().trim()) < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Stock debe ser un entero positivo."); return false; }
        try {
            if (Double.parseDouble(vista.txtPrecio.getText().trim()) <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Precio debe ser un número positivo."); return false; }
        if (vista.cmbMarca.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto."); return false; }
        if (vista.cmbcolor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un color."); return false; }
        return true;
    }

    private void limpiar() {
        vista.txtModelo.setText("");
        vista.txtTalla.setText("");
        vista.txtStock.setText("");
        vista.txtPrecio.setText("");
        vista.jTextField4.setText("");
        if (vista.cmbMarca.getItemCount() > 0) vista.cmbMarca.setSelectedIndex(0);
        if (vista.cmbcolor.getItemCount() > 0) vista.cmbcolor.setSelectedIndex(0);
        vista.jTable2.clearSelection();
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          