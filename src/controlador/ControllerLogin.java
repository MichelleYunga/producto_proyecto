package controlador;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import model.Usuario;
import model.UsuarioJpaController;
import proyecto_producto.ManageFactory;
import vista.viewAdministrador;
import vista.vista;

public class ControllerLogin {

    private ManageFactory manager;
    private vista vista;
    private UsuarioJpaController modelo;
    viewAdministrador vistadmi = new viewAdministrador();

    public ControllerLogin(ManageFactory manager, vista vista, UsuarioJpaController modelo) {
        this.manager = manager;
        this.vista = vista;
        this.modelo = modelo;
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
        iniciarControl();
    }

    public void iniciarControl() {
        vista.getBtnEntrar().addActionListener(l -> controlLogin());
        vista.getBtnCerrar().addActionListener(l -> salir());

    }
public void salir(){
System.exit(0);
}
    public void controlLogin() {
        String usuario = vista.getTxtUsuario().getText();
        String clave = new String(vista.getTxtClave().getPassword());

        try {
            Usuario user = modelo.buscarUsuario(usuario, clave);

            if (user != null) {
                JOptionPane.showMessageDialog(vista, "usuario correcto: " + user.getIdpersona().toString());
                vistadmi.setVisible(true);
                new ControllerAdministrador(vistadmi,manager);
                vista.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(vista, "usuario no encontrado");
            }
        } catch (PersistenceException e) {
            JOptionPane.showMessageDialog(vista,"no exista la conexion con la base de datos");
        }
    }

}
