/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Dimension;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Persona;
import model.PersonaJpaController;
import model.Usuario;
import model.UsuarioJpaController;
import proyecto_producto.ManageFactory;
import view.interna.viewUsuario;

/**
 *
 * @author USUARIO
 */
public class ControllerUsuario {

    viewUsuario vista;
    ManageFactory manage;
    UsuarioJpaController modeloUsuario;
    Usuario usuario;

    JDesktopPane panelEscritorio;
    ModeloTablaUsuario modelotabla;
    ListSelectionModel listaUsernaModel;

    public ControllerUsuario(viewUsuario vista, ManageFactory manage, UsuarioJpaController modeloUsuario, JDesktopPane panelEscritorio) {
        this.manage = manage;
        this.modeloUsuario = modeloUsuario;
        this.panelEscritorio = panelEscritorio;
        this.modelotabla=new ModeloTablaUsuario();
        this.modelotabla.setFilas(this.modeloUsuario.findUsuarioEntities());

        if (ControllerAdministrador.vu == null) {
            ControllerAdministrador.vu = new viewUsuario();
            this.vista = ControllerAdministrador.vu;
            this.panelEscritorio.add(this.vista);
            this.vista.getTableUsuario().setModel(modelotabla);

            this.vista.show();
            cargarCombobos();
            iniciarControl();

            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vista.getSize();
            this.vista.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);

        } else {
            ControllerAdministrador.vu.show();

        }
    }

    
    public void iniciarControl() {
        this.vista.getBtnCrearU().addActionListener(l -> guardarUsuario());
        this.vista.getBtnEditarU().addActionListener(l -> editarUsuario());
        this.vista.getBtnEliminarU().addActionListener(l -> eliminarUsuario());
        this.vista.getTableUsuario().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaUsernaModel = this.vista.getTableUsuario().getSelectionModel();
        listaUsernaModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    usuarioSeleccionado();
                }
            }

            

        });

        this.vista.getBtnEliminarU().setEnabled(false);
        this.vista.getBtnEditarU().setEnabled(false);
        this.vista.getBtnLimpiarFU().addActionListener(l -> limpiar());
        this.vista.getBtnLimpiarBU().addActionListener(l -> limpiarbuscador());
        this.vista.getBtnBuscar().addActionListener(l -> buscarusuario());
        this.vista.getCriterioU().addActionListener(l -> buscarusuario());
    }
    
    
    
    
    /*GUARDAR PERSONA*/
    public void guardarUsuario() {
        usuario = new Usuario();
        usuario.setUsuario(this.vista.getTxtusuario().getText());
        usuario.setClave(this.vista.getPswContrase().getText());
        usuario.setIdpersona((Persona) this.vista.getCmbPersona().getSelectedItem());

        modeloUsuario.create(usuario);
        modelotabla.agregar(usuario);
        Resouces.success("Atención!!", "USUARIO GUARDADA CORECTAMENTE");
        //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA CREADA CORRECTAMENTE");
        limpiar();
    }

    /*EDITAR PERSONA*/
    public void editarUsuario() {
        if (usuario != null) {
            usuario.setUsuario(this.vista.getTxtusuario().getText());
            usuario.setClave(this.vista.getPswContrase().getText());
            usuario.setIdpersona((Persona) this.vista.getCmbPersona().getSelectedItem());
            Resouces.success("Atención!!", "USUARIO EDITADA CORECTAMENTE");
            try {
                modeloUsuario.edit(usuario);
                modelotabla.eliminar(usuario);
                modelotabla.actualizar(usuario);
                limpiar();
            } catch (Exception ex) {

                Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }

            //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA EDITADA CORRECTAMENTE");
        }
    }

    /*ELIMINAR PERSONA*/
    public void eliminarUsuario() {
        if (usuario != null) {
            try {
                modeloUsuario.destroy(usuario.getIdusuario());
                limpiar();
            } catch (Exception ex) {
                Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
                limpiar();
            }
            modelotabla.eliminar(usuario);
            //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA ELIMINADA CORRECTAMENTE");
            Resouces.success("ALERTA!!", "USUARIO ELIMINADO CORECTAMENTE");
        }
    }

    public void limpiar() {
        this.vista.getTxtusuario().setText("");
        this.vista.getPswContrase().setText("");
        this.vista.getCmbPersona().setSelectedItem(0);

        this.vista.getBtnEliminarU().setEnabled(false);
        this.vista.getBtnEditarU().setEnabled(false);
        this.vista.getBtnCrearU().setEnabled(true);
        this.vista.getTableUsuario().getSelectionModel().clearSelection();
    }

    public void usuarioSeleccionado() {
        if (this.vista.getTableUsuario().getSelectedRow() != -1) {
            usuario = modelotabla.getFilas().get(this.vista.getTableUsuario().getSelectedRow());
            this.vista.getTxtusuario().setText(usuario.getUsuario());
            this.vista.getPswContrase().setText(usuario.getClave());
            this.vista.getCmbPersona().setSelectedItem(usuario.getIdpersona());
            //
            this.vista.getBtnEliminarU().setEnabled(true);
            this.vista.getBtnEditarU().setEnabled(true);
            this.vista.getBtnCrearU().setEnabled(false);

        }
    }

    public void buscarusuario() {
        if (this.vista.getCriterioU().isSelected()) {
            modelotabla.setFilas(modeloUsuario.findUsuarioEntities());
            modelotabla.fireTableDataChanged();

        } else {
            if (!this.vista.getTxtbuscar().getText().equals("")) {
                modelotabla.setFilas(modeloUsuario.buscarUsuarios(this.vista.getTxtbuscar().getText()));
                modelotabla.fireTableDataChanged();
            } else {
                limpiarbuscador();
            }

        }

    }
//

    public void limpiarbuscador() {
        this.vista.getTxtbuscar().setText("");
        modelotabla.setFilas(modeloUsuario.findUsuarioEntities());
        modelotabla.fireTableDataChanged();
    }

    public void cargarCombobos() {
        try {
            Vector v = new Vector();
            v.addAll(new PersonaJpaController(manage.getentityManagerFactory()).findPersonaEntities());
            this.vista.getCmbPersona().setModel(new DefaultComboBoxModel(v));

        } catch (ArrayIndexOutOfBoundsException ex) {

            System.out.println("ERROR");
        }
    }

}


