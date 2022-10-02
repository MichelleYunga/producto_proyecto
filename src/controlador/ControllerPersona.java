package controlador;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Persona;
import model.PersonaJpaController;
import modelo.exceptions.NonexistentEntityException;
import proyecto_producto.ManageFactory;
import view.interna.viewPersonas;

/**
 *
 * @author USUARIO
 */
public class ControllerPersona {

    ModeloTablaPersona modelotabla = new ModeloTablaPersona();

    viewPersonas vista;
    ManageFactory manage;
    PersonaJpaController modeloPersona;
    Persona persona;

    JDesktopPane panelEscritorio;

    ListSelectionModel listaPersonaModel;

    public ControllerPersona(viewPersonas vista, ManageFactory manage, PersonaJpaController modeloPersona, JDesktopPane panelEscritorio) {

        this.manage = manage;
        this.modeloPersona = modeloPersona;
        this.panelEscritorio = panelEscritorio;
        this.modelotabla = new ModeloTablaPersona();
        this.modelotabla.setFilas(modeloPersona.findPersonaEntities());

        if (ControllerAdministrador.vp == null) {
            ControllerAdministrador.vp = new viewPersonas();
            this.vista = ControllerAdministrador.vp;
            this.panelEscritorio.add(this.vista);
            this.vista.getTablePersona().setModel(modelotabla);
            this.vista.show();
            iniciarControl();
            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vista.getSize();
            this.vista.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);

        } else {
            ControllerAdministrador.vp.show();

        }
    }

    public void iniciarControl() {
        this.vista.getBtnCrear().addActionListener(l -> guardarPersona());
        this.vista.getBtnEditar().addActionListener(l -> editarPersona());
        this.vista.getBtnEliminar().addActionListener(l -> eliminarPersona());
        this.vista.getTablePersona().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPersonaModel = this.vista.getTablePersona().getSelectionModel();
        listaPersonaModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    personaSeleccionada();
                }
            }

        });

        this.vista.getBtnEliminar().setEnabled(false);
        this.vista.getBtnEditar().setEnabled(false);
        this.vista.getBtnLimpiar().addActionListener(l -> limpiar());
        this.vista.getBtnLimpiarB().addActionListener(l -> limpiarbuscador());
        this.vista.getBtnBuscar().addActionListener(l -> buscarpersona());
        this.vista.getCriterio().addActionListener(l -> buscarpersona());
        this.vista.getBtnreporteGen().addActionListener(l->reporteGeneral());
        this.vista.getBtnreporteIn().addActionListener(l->reporteIndividual());
    }

    /*GUARDAR PERSONA*/
    public void guardarPersona() {
        persona = new Persona();
        persona.setNombre(this.vista.getTxtnombre().getText());
        persona.setApellido(this.vista.getTxtapellido().getText());
        persona.setCedula(this.vista.getTxtcedula().getText());
        persona.setCelular(this.vista.getTxtcelular().getText());
        persona.setCorreo(this.vista.getTxtcorreo().getText());
        persona.setDireccion(this.vista.getTxtdireccion().getText());
        modeloPersona.create(persona);
        modelotabla.agregar(persona);
        Resouces.success("Atención!!", "PERSONA GUARDADA CORECTAMENTE");
        //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA CREADA CORRECTAMENTE");
        limpiar();
    }

    /*EDITAR PERSONA*/
    public void editarPersona() {
        if (persona != null) {
            persona.setNombre(this.vista.getTxtnombre().getText());
            persona.setApellido(this.vista.getTxtapellido().getText());
            persona.setCedula(this.vista.getTxtcedula().getText());
            persona.setCelular(this.vista.getTxtcelular().getText());
            persona.setCorreo(this.vista.getTxtcorreo().getText());
            persona.setDireccion(this.vista.getTxtdireccion().getText());
            try {
                modeloPersona.edit(persona);

            } catch (Exception ex) {

                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
            modelotabla.eliminar(persona);
            modelotabla.actualizar(persona);
                    Resouces.success("Atención!!", "PERSONA EDITADA CORECTAMENTE");

            limpiar();
            //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA EDITADA CORRECTAMENTE");

        }
    }

    /*ELIMINAR PERSONA*/
    
     public void eliminarPersona() {
        if (persona != null) {
            try {
                modeloPersona.destroy(persona.getIdpersona());
                limpiar();
            } catch (Exception ex) {
               Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
            modelotabla.eliminar(persona);
          //  JOptionPane.showMessageDialog(panelEscritorio, "PERSONA ELIMINADA CORRECTAMENTE");
            Resouces.success("ALERTA!!", "PERSONA ELIMINADA CORECTAMENTE");
        }
    }
    public void limpiar() {
        this.vista.getTxtnombre().setText("");
        this.vista.getTxtapellido().setText("");
        this.vista.getTxtcedula().setText("");
        this.vista.getTxtcelular().setText("");
        this.vista.getTxtcorreo().setText("");
        this.vista.getTxtdireccion().setText("");
        this.vista.getBtnEliminar().setEnabled(false);
        this.vista.getBtnEditar().setEnabled(false);
        this.vista.getBtnCrear().setEnabled(true);
        this.vista.getTablePersona().getSelectionModel().clearSelection();
    }

    public void personaSeleccionada() {
        if (this.vista.getTablePersona().getSelectedRow() != -1) {
            persona = modelotabla.getFilas().get(this.vista.getTablePersona().getSelectedRow());
            this.vista.getTxtnombre().setText(persona.getNombre());
            this.vista.getTxtapellido().setText(persona.getApellido());
            this.vista.getTxtcedula().setText(persona.getCedula());
            this.vista.getTxtcelular().setText(persona.getCelular());
            this.vista.getTxtcorreo().setText(persona.getCorreo());
            this.vista.getTxtdireccion().setText(persona.getDireccion());
            this.vista.getBtnEliminar().setEnabled(true);
            this.vista.getBtnEditar().setEnabled(true);
            this.vista.getBtnCrear().setEnabled(false);

        }
    }

    public void buscarpersona() {
        if (this.vista.getCriterio().isSelected()) {
            modelotabla.setFilas(modeloPersona.findPersonaEntities());
            modelotabla.fireTableDataChanged();

        } else {
            if (!this.vista.getTxtbuscar().getText().equals("")) {
                modelotabla.setFilas(modeloPersona.buscarPersona(this.vista.getTxtbuscar().getText()));
                modelotabla.fireTableDataChanged();
            } else {
                limpiarbuscador();
            }

        }

    }
//

    public void limpiarbuscador() {
        this.vista.getTxtbuscar().setText("");
        modelotabla.setFilas(modeloPersona.findPersonaEntities());
        modelotabla.fireTableDataChanged();
    }
    
     //llamar
   public void reporteGeneral() {
        Resouces.imprimirReeporte(ManageFactory.getConnection(manage.getentityManagerFactory().createEntityManager()), "/reportes/Persona.jasper", new HashMap());
    }
    

   public void reporteIndividual(){
    if(persona!=null){
    Map parameters =new  HashMap ();
    parameters.put("id",persona.getIdpersona());
    Resouces.imprimirReeporte(ManageFactory.getConnection(manage.getentityManagerFactory().createEntityManager()),"/reportes/individual.jasper",parameters);
    }else{
    Resouces.warning("Atencio!!","De seleccionar una persona");
    
    }
} 
    
}
