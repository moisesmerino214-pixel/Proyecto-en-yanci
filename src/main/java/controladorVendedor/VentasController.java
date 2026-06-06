/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorVendedor;

import DAOVendedor.VentasDAO;
import arbol.ArbolBBusqueda;
import interfazVendedor.VentasInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modeloVendedor.DetalleVenta;
import modeloVendedor.Ventas;
import vistaVendedor.VentasView;

/**
 *
 * @author moise
 */
public class VentasController implements ActionListener {
     private VentasView view;
     private VentasInterfaz dao;
     private ArbolBBusqueda<DetalleVenta> carrito = new ArbolBBusqueda<>();
     private int contadorCarrito = 0;
     private vistaVendedor.MenuVendedorView menu;

    public VentasController(VentasView view, vistaVendedor.MenuVendedorView menu) {
        this.view = view;
        this.menu = menu;
        this.dao = new VentasDAO();
        
        try {
            dao.cargarClientes(view.comboCliente);
            dao.cargarVendedores(view.comboVendedor);
            dao.cargarProductos(view.comboProducto);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar datos iniciales:\n" + ex.getMessage());
        }
        
        view.comboProducto.addActionListener(e -> {
            if (view.comboProducto.getSelectedItem() == null) 
                return;
            try {
                String producto = view.comboProducto.getSelectedItem().toString();
                VentasDAO ventasDAO = (VentasDAO) dao;
                double precio = ventasDAO.cargarColoresYObtenerPrecio(view.comboColor, producto);
                view.txtPrecio.setText(String.format("%.2f", precio));
                view.comboTalla.removeAllItems();
 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error al cargar colores/precio:\n" + ex.getMessage());
            }
        });
        
        view.comboColor.addActionListener(e -> {
            if (view.comboProducto.getSelectedItem() == null ||
                view.comboColor.getSelectedItem() == null) 
                return;
            try {
                String producto = view.comboProducto.getSelectedItem().toString();
                String color    = view.comboColor.getSelectedItem().toString();
                dao.cargarTallas(view.comboTalla, producto, color);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error al cargar tallas:\n" + ex.getMessage());
            }
        });
        
        view.txtPrecio.setEditable(false);
        view.btnAgregar.addActionListener(this);
        view.btnEliminar.addActionListener(this);
        view.btnRealizarVenta.addActionListener(this);
        view.btnSalir.addActionListener(this);
        view.btnAgregarNuevo.addActionListener(e -> agregarClienteRapido());
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btnAgregar)       agregarAlCarrito();
        if (e.getSource() == view.btnEliminar)      eliminarDelCarrito();
        if (e.getSource() == view.btnRealizarVenta) realizarVenta();
        if (e.getSource() == view.btnSalir) { 
            view.dispose();  
            menu.setVisible(true);
        }
    }
    
    private void agregarClienteRapido() {
    javax.swing.JTextField txtNombre   = new javax.swing.JTextField();
    javax.swing.JTextField txtDui      = new javax.swing.JTextField();
    javax.swing.JTextField txtTelefono = new javax.swing.JTextField();
    javax.swing.JTextField txtCorreo   = new javax.swing.JTextField();

    Object[] campos = {
        "Nombre:",   txtNombre,
        "DUI:",      txtDui,
        "Teléfono:", txtTelefono,
        "Correo:",   txtCorreo
    };

    int resultado = JOptionPane.showConfirmDialog(
        view, campos, "Agregar Cliente Nuevo",
        JOptionPane.OK_CANCEL_OPTION
    );

    if (resultado == JOptionPane.OK_OPTION) {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(view, "El nombre es obligatorio.");
            return;
        }
        try {
            DAO.ClienteDAO clienteDAO = new DAO.ClienteDAO();
            modelo.Cliente c = new modelo.Cliente(
                0,
                nombre,
                txtDui.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim()
            );
            clienteDAO.agregar(c);
            dao.cargarClientes(view.comboCliente);
            view.comboCliente.setSelectedIndex(view.comboCliente.getItemCount() - 1);
            JOptionPane.showMessageDialog(view, "Cliente agregado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al agregar cliente:\n" + ex.getMessage());
        }
    }
}
    
    private void agregarAlCarrito() {
        if (view.comboProducto.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Seleccione un producto."); 
            return;
        }
        if (view.comboColor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Seleccione un color."); 
            return;
        }
        if (view.comboTalla.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Seleccione una talla."); 
            return;
        }
        if (view.txtCantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ingrese la cantidad."); 
            return;
        }
 
        try {
            String producto = view.comboProducto.getSelectedItem().toString();
            String color    = view.comboColor.getSelectedItem().toString();
            String talla    = view.comboTalla.getSelectedItem().toString();
            double precio   = Double.parseDouble(view.txtPrecio.getText().trim());
            int cantidad    = Integer.parseInt(view.txtCantidad.getText().trim());
 
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(view, "La cantidad debe ser mayor a cero."); 
                return;
            }
 
            int idInventario = dao.obtenerIdInventario(producto, color, talla);
            if (idInventario == 0) {
                JOptionPane.showMessageDialog(view,
                    "No se encontró inventario para esa combinación."); 
                return;
            }
            
            int stockDisponible = ((VentasDAO) dao).obtenerStock(idInventario);
            if (cantidad > stockDisponible) {
                JOptionPane.showMessageDialog(view, "Stock insuficiente. Disponible: " + stockDisponible);
                return;
            }

            
            
            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdDetalleVenta(++contadorCarrito);
            detalle.setProducto(producto);
            detalle.setColor(color);
            detalle.setTalla(talla);
            detalle.setPrecioUnitario(precio);
            detalle.setCantidad(cantidad);
            detalle.setIdInventario(idInventario);
            carrito.insertar(detalle);
            actualizarTabla();
 
            view.txtCantidad.setText("");
 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "La cantidad debe ser un número entero.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al agregar al carrito:\n" + ex.getMessage());
        }
    }
    
    private void eliminarDelCarrito() {
        int fila = view.tablaDetalleVenta.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(view,
                "Seleccione un producto de la tabla para eliminarlo."); return;
        }
        int contador = 0;
        DetalleVenta aEliminar = null;
        for (Object obj : carrito.IND()) {
            if (contador == fila) {
                aEliminar = (DetalleVenta) obj;
                break;
            }
            contador++;
        }
        if (aEliminar != null) {
            carrito.eliminar(aEliminar);
            actualizarTabla();
        }
    }
    
    private void realizarVenta() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(view, "El carrito está vacío. Agregue al menos un producto."); return;
        }
        if (view.comboCliente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Seleccione un cliente."); return;
        }
        if (view.comboVendedor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Seleccione un vendedor."); return;
        }
 
        try {
            int idCliente  = Integer.parseInt(
                view.comboCliente.getSelectedItem().toString().split(" - ")[0].trim());
            int idVendedor = Integer.parseInt(
                view.comboVendedor.getSelectedItem().toString().split(" - ")[0].trim());

            double total = 0;
            for (Object obj : carrito.IND()) {
                total += ((DetalleVenta) obj).getSubtotal();
            }
 
            String fecha = obtenerFecha();
            Ventas venta = new Ventas();
            venta.setIdCliente(idCliente);
            venta.setIdVendedor(idVendedor);
            venta.setFecha(fecha);
            venta.setTotal(total);
 
            int idVenta = dao.registrarVenta(venta);
            for (Object obj : carrito.IND()) {
                DetalleVenta detalle = (DetalleVenta) obj;
                detalle.setIdVenta(idVenta);
                dao.registrarDetalle(detalle);
                ((VentasDAO) dao).descontarStock(detalle.getIdInventario(), detalle.getCantidad());
            }
 
            JOptionPane.showMessageDialog(view, "¡Venta #" + idVenta + " realizada con éxito!\nTotal: $" + String.format("%.2f", total));
            carrito       = new ArbolBBusqueda<>();
            contadorCarrito = 0;
            actualizarTabla();
            limpiarFormulario();
 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al realizar la venta:\n" + ex.getMessage());
        }
    }
    
    private void actualizarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) view.tablaDetalleVenta.getModel();
        modelo.setRowCount(0);
        double totalAcumulado = 0;
        for (Object obj : carrito.IND()) {
            DetalleVenta d = (DetalleVenta) obj;
            double subtotal = d.getSubtotal();
            totalAcumulado += subtotal;
            modelo.addRow(new Object[]{
                d.getIdDetalleVenta(),
                d.getProducto(),
                d.getColor(),
                d.getTalla(),
                String.format("$%.2f", d.getPrecioUnitario()),
                d.getCantidad(),
                String.format("$%.2f", subtotal),
                String.format("$%.2f", totalAcumulado)
            });
        }
    }
 
    private String obtenerFecha() {
        if (view.calendario.getDate() != null)
            return new SimpleDateFormat("yyyy-MM-dd").format(view.calendario.getDate());
        return new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    }
    
    
 
    private void limpiarFormulario() {
        view.txtCantidad.setText("");
        view.txtPrecio.setText("");
        view.comboCliente.setSelectedIndex(0);
        view.comboVendedor.setSelectedIndex(0);
        view.comboProducto.setSelectedIndex(0);
        view.comboColor.removeAllItems();
        view.comboTalla.removeAllItems();
        view.calendario.setDate(null);
    }
}