/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author USUARIO
 */
public class viewAdministrador extends javax.swing.JFrame {

    /**
     * Creates new form viewAdministrador
     */
    public viewAdministrador() {
        initComponents();
    }

    public JMenuItem getItemPersona() {
        return itemPersona;
    }

    public void setItemPersona(JMenuItem itemPersona) {
        this.itemPersona = itemPersona;
    }

    public JMenuItem getItemProducto() {
        return itemProducto;
    }

    public void setItemProducto(JMenuItem itemProducto) {
        this.itemProducto = itemProducto;
    }

    public JMenuItem getItemUsuario() {
        return itemUsuario;
    }

    public void setItemUsuario(JMenuItem itemUsuario) {
        this.itemUsuario = itemUsuario;
    }

    public JDesktopPane getEscritorio() {
        return escritorio;
    }

    public void setEscritorio(JDesktopPane escritorio) {
        this.escritorio = escritorio;
    }

  

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        escritorio = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuConfiguracion = new javax.swing.JMenu();
        itemPersona = new javax.swing.JMenuItem();
        itemUsuario = new javax.swing.JMenuItem();
        itemProducto = new javax.swing.JMenuItem();
        menuReportes = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        escritorio.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 733, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 362, Short.MAX_VALUE)
        );

        menuConfiguracion.setBackground(new java.awt.Color(153, 153, 255));
        menuConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        menuConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnconfig.png"))); // NOI18N

        itemPersona.setText("PERSONAS");
        menuConfiguracion.add(itemPersona);

        itemUsuario.setText("USUARIO");
        menuConfiguracion.add(itemUsuario);

        itemProducto.setText("PRODUCTOS");
        menuConfiguracion.add(itemProducto);

        jMenuBar1.add(menuConfiguracion);

        menuReportes.setBackground(new java.awt.Color(153, 153, 255));
        menuReportes.setForeground(new java.awt.Color(0, 0, 0));
        menuReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btnReportes.png"))); // NOI18N
        jMenuBar1.add(menuReportes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane escritorio;
    public javax.swing.JMenuItem itemPersona;
    public javax.swing.JMenuItem itemProducto;
    public javax.swing.JMenuItem itemUsuario;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenu menuConfiguracion;
    public javax.swing.JMenu menuReportes;
    // End of variables declaration//GEN-END:variables
}
