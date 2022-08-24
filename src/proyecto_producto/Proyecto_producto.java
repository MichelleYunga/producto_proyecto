package proyecto_producto;

import controlador.ControllerLogin;
import model.UsuarioJpaController;
import vista.vista;

public class Proyecto_producto {

    public static void main(String[] args) {
   ManageFactory manager= new ManageFactory();
    vista vista= new vista();
   
   UsuarioJpaController modelo =new UsuarioJpaController(manager.getentityManagerFactory());
    ControllerLogin controlador = new ControllerLogin(manager, vista, modelo);
    
}

}
