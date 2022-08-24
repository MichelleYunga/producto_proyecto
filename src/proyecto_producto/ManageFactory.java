
package proyecto_producto;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ManageFactory {
    private EntityManagerFactory emf=null;
    public EntityManagerFactory  getentityManagerFactory(){
    return emf=Persistence.createEntityManagerFactory("proyecto_productoPU");
    }
}
