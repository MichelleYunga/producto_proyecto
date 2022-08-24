/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import model.PersonaJpaController;
import model.ProductoJpaController;
import model.UsuarioJpaController;
import proyecto_producto.ManageFactory;
import view.interna.viewPersonas;
import view.interna.viewProductos;
import view.interna.viewUsuario;
import vista.viewAdministrador;

/**
 *
 * @author USUARIO
 */
public class ControllerAdministrador extends javax.swing.JFrame {

    viewAdministrador vista;
    ManageFactory manage;

    public ControllerAdministrador(viewAdministrador vista, ManageFactory manage) {
        this.vista = vista;
        this.manage = manage;
        this.vista.setExtendedState(MAXIMIZED_BOTH);
        controleventos();
    }

    public void controleventos() {
        this.vista.getItemPersona().addActionListener(l -> cargarvistaPersona());
        this.vista.getItemProducto().addActionListener(l -> cargarvistaProducto());
        this.vista.getItemUsuario().addActionListener(l -> cargarvistaUsuario());

    }

    public static viewPersonas vp;
    public static viewProductos vps;
    public static viewUsuario vu;

    public void cargarvistaPersona() {
        new ControllerPersona(vp, manage, new PersonaJpaController(manage.getentityManagerFactory()), this.vista.getEscritorio());
//        System.out.println("abierto");

    }

    public void cargarvistaProducto() {
        new ControllerProducto(vps, manage, new ProductoJpaController(manage.getentityManagerFactory()), this.vista.getEscritorio());
//        System.out.println("abierto");

    }

    public void cargarvistaUsuario() {
        new ControllerUsuario(vu, manage, new UsuarioJpaController(manage.getentityManagerFactory()), this.vista.getEscritorio());
//        System.out.println("abierto");

    }
}
