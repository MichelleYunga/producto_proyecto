/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Producto;
import model.ProductoJpaController;
import proyecto_producto.ManageFactory;
import view.interna.viewProductos;

/**
 *
 * @author USUARIO
 */
public class ControllerProducto {

    viewProductos vista;
    ManageFactory manage;
    ProductoJpaController modeloProducto;
    Producto producto;

    JDesktopPane panelEscritorio;
    ModeloTablaProducto modelotabla;
    ListSelectionModel listaProductnaModel;

    public ControllerProducto(viewProductos vista, ManageFactory manage, ProductoJpaController modeloProducto, JDesktopPane panelEscritorio) {

        this.manage = manage;
        this.modeloProducto = modeloProducto;
        this.panelEscritorio = panelEscritorio;
        this.modelotabla = new ModeloTablaProducto();
        this.modelotabla.setFilas(this.modeloProducto.findProductoEntities());

        if (ControllerAdministrador.vps == null) {
            ControllerAdministrador.vps = new viewProductos();
            this.vista = ControllerAdministrador.vps;
            this.panelEscritorio.add(this.vista);
            this.vista.getTableProducto().setModel(modelotabla);
            iniciarControl();

            ControllerAdministrador.vps.show();

            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vista.getSize();
            this.vista.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);

        } else {
            ControllerAdministrador.vps.show();

        }

    }

    public void iniciarControl() {
        this.vista.getBtnCrearP().addActionListener(l -> guardarProducto());
        this.vista.getBtnEditarP().addActionListener(l -> editarProducto());
        this.vista.getBtnEliminarP().addActionListener(l -> eliminarProducto());
        this.vista.getTableProducto().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaProductnaModel = this.vista.getTableProducto().getSelectionModel();
        listaProductnaModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    productoSeleccionado();
                }
            }

        });

        this.vista.getBtnEliminarP().setEnabled(false);
        this.vista.getBtnEditarP().setEnabled(false);
        this.vista.getBtnLimpiar().addActionListener(l -> limpiar());
        this.vista.getBtnLimpiarBP().addActionListener(l -> limpiarbuscador());
        this.vista.getBtnBuscar().addActionListener(l -> buscarproducto());
        this.vista.getCriterio().addActionListener(l -> buscarproducto());
    }

    /*GUARDAR PERSONA*/
    public void guardarProducto() {
        producto = new Producto();
        producto.setNombre(this.vista.getTxtnombre().getText());
        double precio = Double.parseDouble(this.vista.getTxtprecio().getText());
        producto.setPrecio(precio);
        Integer cantidad = Integer.parseInt(this.vista.getTxtcantidad().getText());
        producto.setCantidad(cantidad);

        modeloProducto.create(producto);
        modelotabla.agregar(producto);
        Resouces.success("Atención!!", "PRODUCTO GUARDADA CORECTAMENTE");
        //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA CREADA CORRECTAMENTE");
        limpiar();
    }

    /*EDITAR PERSONA*/
    public void editarProducto() {
        if (producto != null) {
            producto.setNombre(this.vista.getTxtnombre().getText());
            double precio = Double.parseDouble(this.vista.getTxtprecio().getText());
            producto.setPrecio(precio);
            Integer cantidad = Integer.parseInt(this.vista.getTxtcantidad().getText());
            producto.setCantidad(cantidad);
            Resouces.success("Atención!!", "PRODUCTO EDITADA CORECTAMENTE");
            try {
                modeloProducto.edit(producto);
                modelotabla.eliminar(producto);
                modelotabla.actualizar(producto);

            } catch (Exception ex) {

                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, ex);
            }

            limpiar();
            //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA EDITADA CORRECTAMENTE");

        }
    }

    /*ELIMINAR PERSONA*/
    public void eliminarProducto() {
        if (producto != null) {
            try {
                modeloProducto.destroy(producto.getIdproducto());

            } catch (Exception ex) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, ex);
                limpiar();
            }
            modelotabla.eliminar(producto);
            //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA ELIMINADA CORRECTAMENTE");
            Resouces.success("ALERTA!!", "PRODUCTO ELIMINADA CORECTAMENTE");
        }
    }

    public void limpiar() {
        this.vista.getTxtnombre().setText("");
        this.vista.getTxtprecio().setText("");
        this.vista.getTxtcantidad().setText("");

        this.vista.getBtnEliminarP().setEnabled(false);
        this.vista.getBtnEditarP().setEnabled(false);
        this.vista.getBtnCrearP().setEnabled(true);
        this.vista.getTableProducto().getSelectionModel().clearSelection();
    }

    public void productoSeleccionado() {
        if (this.vista.getTableProducto().getSelectedRow() != -1) {
            producto = modelotabla.getFilas().get(this.vista.getTableProducto().getSelectedRow());
            this.vista.getTxtnombre().setText(producto.getNombre());
            String precio = String.valueOf(producto.getPrecio());
            this.vista.getTxtprecio().setText(precio);
            String cantidad = String.valueOf(producto.getCantidad());
            this.vista.getTxtcantidad().setText(cantidad);
            //
            this.vista.getBtnEliminarP().setEnabled(true);
            this.vista.getBtnEditarP().setEnabled(true);
            this.vista.getBtnCrearP().setEnabled(false);

        }
    }

    public void buscarproducto() {
        if (this.vista.getCriterio().isSelected()) {
            modelotabla.setFilas(modeloProducto.findProductoEntities());
            modelotabla.fireTableDataChanged();

        } else {
            if (!this.vista.getTxtbuscar().getText().equals("")) {
                modelotabla.setFilas(modeloProducto.buscarProducto(this.vista.getTxtbuscar().getText()));
                modelotabla.fireTableDataChanged();
            } else {
                limpiarbuscador();
            }

        }

    }
//

    public void limpiarbuscador() {
        this.vista.getTxtbuscar().setText("");
        modelotabla.setFilas(modeloProducto.findProductoEntities());
        modelotabla.fireTableDataChanged();
    }
}
