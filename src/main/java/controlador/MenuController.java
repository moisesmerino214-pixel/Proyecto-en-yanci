/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controladorVendedor.VentasController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ClienteVista;
import vista.EmpleadoVista;
import vista.InventarioView;
import vista.MenuView;
import vista.ProveedoresView;
import vistaVendedor.VentasView;
import vista.ViewProducto;

/**
 *
 * @author moise
 */
public class MenuController implements ActionListener{

    private MenuView vista;

    public MenuController(MenuView vista) {
        this.vista = vista;
        
        this.vista.btnCliente.addActionListener(this);
        this.vista.btnVentas.addActionListener(this);
        this.vista.btnMasVendidos.addActionListener(this);
        this.vista.btnMasVendidos.addActionListener(this);
        this.vista.btnInventario.addActionListener(this);
        this.vista.btnProducto.addActionListener(this);
        this.vista.btnEmpleado.addActionListener(this);
        this.vista.btnProveedor.addActionListener(this);
        
        this.vista.btnCliente.setActionCommand("Cliente");
        this.vista.btnVentas.setActionCommand("Ventas");
        this.vista.btnMasVendidos.setActionCommand("MasVendido");
        this.vista.btnInventario.setActionCommand("Inventario");
        this.vista.btnProducto.setActionCommand("Productos");
        this.vista.btnEmpleado.setActionCommand("Empleado");
        this.vista.btnProveedor.setActionCommand("Proveedor");
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String menu = e.getActionCommand();
        
        switch(menu){
            case "Cliente":
              ClienteVista cliente = new ClienteVista();
              DAO.ClienteDAO dao = new DAO.ClienteDAO();
              new ClienteController(cliente, dao);
            cliente.setVisible(true);
            cliente.setLocationRelativeTo(null);
            break;
            
            case "Ventas":
                vista.VentasView gestionVentas = new vista.VentasView();
                new GestionVentasController(gestionVentas);
                javax.swing.JFrame frameVentas = new javax.swing.JFrame("Gestión de Ventas");
                frameVentas.setContentPane(gestionVentas);
                frameVentas.pack();
                frameVentas.setLocationRelativeTo(null);
                frameVentas.setVisible(true);
            break;
            
            case "MasVendido":
              vista.MasVendidosView vistaMasVendidos = new vista.MasVendidosView();
    
                new controlador.MasVendidosController(vistaMasVendidos);
                javax.swing.JFrame frame = new javax.swing.JFrame("Reporte de Más Vendidos");
                frame.add(vistaMasVendidos);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            break;
            
            case "Inventario":
              InventarioView inventario = new InventarioView();
              DAO.InventarioDAO daoInventario = new DAO.InventarioDAO();
              new InventarioController(inventario, daoInventario);
            inventario.setVisible(true);
            break;
            
            case "Productos":
              ViewProducto producto = new ViewProducto();
              new ProductoController(producto);
            producto.setVisible(true);
            break;
            
            case "Empleado":
              EmpleadoVista empleado = new EmpleadoVista();
              new EmpeladoController(empleado);
            empleado.setVisible(true);
            break;
            
            case "Proveedor":
              ProveedoresView proveedor = new ProveedoresView();
              new ProveedorController(proveedor);
              proveedor.setLocationRelativeTo(null);
            proveedor.setVisible(true);
            break;
            
            case " Salir":
                
            break;
            
            default:
            System.out.println("Acción no reconocida");
            break;
        }
    }
    
}
