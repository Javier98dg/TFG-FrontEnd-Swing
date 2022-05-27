package com.frontswing.client.controller;

import com.frontswing.client.entity.SicatpersoEntity;
import com.frontswing.client.gui.TercerosFrameGUI;
import com.frontswing.client.gui.TercerosItemDialog;
import com.frontswing.client.gui.tablemodel.SicatpersoTableModel;
import com.frontswing.client.rest.TercerosRC;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author jjdg46
 */
public class TercerosController {

    private TercerosFrameGUI view;
    private TercerosItemDialog tercerosItemDialog;
    boolean crear;

    public void initController() {
        view = new TercerosFrameGUI();
        
        //Cargar elementos
        setBotonEnabled(false);
        loadTable2(new SicatpersoEntity[0]);
        view.getJTableElemento().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Muestra todos los datos en la tabla
        SicatpersoEntity[] sicatperso = TercerosRC.getAll();
        loadTable2(sicatperso);

        //Cargar eventos
        view.getJTextFieldSearch().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search();
            }
        }); 
        view.getJButtonSalir().addActionListener(e -> actionSalir());
        view.getJTableElemento().getSelectionModel().addListSelectionListener(tableSelectionEvent -> actionSelectRow());
        view.getJButtonAdd().addActionListener(e -> actionAdd());
        view.getJButtonUpdate().addActionListener(e -> actionUpdate());
        view.getJButtonDel().addActionListener(e -> actionDel());
    }
    
    public void initControllerDialog(){
        tercerosItemDialog = new TercerosItemDialog(view, true);
        tercerosItemDialog.getJButtonOK().addActionListener(e -> actionOK());
        tercerosItemDialog.getJButtonCanc().addActionListener(e -> actionCanc());
    }

    public void displayView() {
        view.setVisible(true);
    }
    
    public void noViewDialog(){
        tercerosItemDialog.setVisible(false);
    }
    
    //Filtro NIF
    private void search(){
        String termSearched = view.getJTextFieldSearch().getText();
        
        if(termSearched.isEmpty()){
            setBotonEnabled(false);
            loadTable2(new SicatpersoEntity[0]);
            SicatpersoEntity[] sicatperso = TercerosRC.getAll();
            //Page<SicatpersoEntity> sicatperso = TercerosRC.getAll();
            loadTable2(sicatperso);
        }
        else{
            SicatpersoEntity sicatperso = TercerosRC.searchPersoById(termSearched);
            this.loadTable(sicatperso);
        }
        if(view.getJTableElemento().getSelectedRow() !=-1){
            setBotonEnabled(true);
        }else{
            setBotonEnabled(false);
            loadTextFields(null);
        }
    }
    
    private void resetFields(){
        view.getJTextFieldSearch().setText("");
    }
    
    private void resetFieldsDialog(){
        tercerosItemDialog.getJTextFieldApe1().setText("");
        tercerosItemDialog.getJTextFieldApe2().setText("");
        tercerosItemDialog.getJTextFieldCPos().setText("");
        tercerosItemDialog.getJTextFieldCorreo().setText("");
        tercerosItemDialog.getJTextFieldFAct().setText("");
        tercerosItemDialog.getJTextFieldMunic().setText("");
        tercerosItemDialog.getJTextFieldNIF().setText("");
        tercerosItemDialog.getJTextFieldNombre().setText("");
        tercerosItemDialog.getJTextFieldProvin().setText("");
        tercerosItemDialog.getJTextFieldTelef().setText("");
        tercerosItemDialog.getJTextFieldUser().setText("");
        tercerosItemDialog.getJTextFieldDirec().setText("");
    }
    
    //Si se ha seleccionado una fila de la tabla
    //Desbloquea boton Borrar y Actualizar
    private void setBotonEnabled(boolean enabled){
        view.getJButtonDel().setEnabled(enabled);
        view.getJButtonUpdate().setEnabled(enabled);
        
        if(!enabled){
            view.getJTextFieldSearch().setText("");
        }
    }
    //
    private void loadTable2(/*Page<SicatpersoEntity> rows*/SicatpersoEntity[] rows){
        SicatpersoTableModel tableModel = new SicatpersoTableModel(Arrays.asList(rows));
        view.getJTableElemento().setModel(tableModel);
        
        if(rows.length>0){
            view.getJTableElemento().setEnabled(true);
        }else{
            view.getJTableElemento().setEnabled(false);            
        }
    }
    
    //Carga tabla con las filas
    private void loadTable(SicatpersoEntity rows){
        SicatpersoTableModel tableModel = new SicatpersoTableModel(Arrays.asList(rows));

        view.getJTableElemento().setModel(tableModel);
        view.getJTableElemento().setEnabled(true);
    }
    
    //Pulsar botón crear
    private void actionAdd() {
        try {
            crear=true;
            tercerosItemDialog.getJTextFieldNIF().setEditable(true);
            tercerosItemDialog.getJTextFieldUser().setEditable(true);
            tercerosItemDialog.setVisible(true);  
            
        } catch (Exception ex) {
            Logger.getLogger(TercerosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Pulsar botón actualizar con fila seleccionada
    private void actionUpdate() {
        try {
            crear=false;
            tercerosItemDialog.getJTextFieldNIF().setEditable(false);
            tercerosItemDialog.getJTextFieldUser().setEditable(false);
            
            int selectedRow = view.getJTableElemento().getSelectedRow();
            if (selectedRow == -1) return;

            SicatpersoEntity elemento = (SicatpersoEntity) view.getJTableElemento().getValueAt(selectedRow, -1);

            String NIF = elemento.getId();
            String Usuario = elemento.getUsuario();
            String PApellido = elemento.getPrimerApe();
            String Nombre,SApellido,Correo,Direccion;
            Integer Telefono,Municipio,CP,Provincia;
            String TelefonoAux,MunicipioAux,CPAux,ProvinciaAux;
            
            if(elemento.getTelefono()==null){
                TelefonoAux = "";
                tercerosItemDialog.getJTextFieldTelef().setText(TelefonoAux);
            }
            else {
                Telefono = elemento.getTelefono();
                tercerosItemDialog.getJTextFieldTelef().setText(Integer.toString(Telefono));
            }
            if(elemento.getCodMunicipio()==null){
                MunicipioAux = "";
                tercerosItemDialog.getJTextFieldMunic().setText(MunicipioAux);
            }
            else {
                Municipio = elemento.getCodMunicipio();
                tercerosItemDialog.getJTextFieldMunic().setText(Integer.toString(Municipio));
            }
            if(elemento.getCodPostal()==null){
                CPAux = "";
                tercerosItemDialog.getJTextFieldCPos().setText(CPAux);
            }
            else {
                CP = elemento.getCodPostal();
                tercerosItemDialog.getJTextFieldCPos().setText(Integer.toString(CP));
            }
            if(elemento.getCodProvincia()==null){
                ProvinciaAux = "";
                tercerosItemDialog.getJTextFieldProvin().setText(ProvinciaAux);
            }
            else {
                Provincia = elemento.getCodProvincia();
                tercerosItemDialog.getJTextFieldProvin().setText(Integer.toString(Provincia));
            }

            if(elemento.getNombre() == null) Nombre = "";
            else Nombre = elemento.getNombre();
            if(elemento.getSegundoApe() == null) SApellido = "";
            else SApellido = elemento.getSegundoApe();
            if(elemento.getCorreoElec() == null) Correo = "";
            else Correo = elemento.getCorreoElec();
            if(elemento.getDireccion()== null) Direccion = "";
            else Direccion = elemento.getDireccion();
            
            LocalDateTime fecAc = elemento.getFechaActualizacion();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String FechaAct = fecAc.format(dateTimeFormatter);

            tercerosItemDialog.getJTextFieldNIF().setText(NIF);
            tercerosItemDialog.getJTextFieldNombre().setText(Nombre);
            tercerosItemDialog.getJTextFieldApe1().setText(PApellido);
            tercerosItemDialog.getJTextFieldApe2().setText(SApellido);
            tercerosItemDialog.getJTextFieldCorreo().setText(Correo);
            tercerosItemDialog.getJTextFieldFAct().setText(FechaAct);
            tercerosItemDialog.getJTextFieldDirec().setText(Direccion);
            tercerosItemDialog.getJTextFieldUser().setText(Usuario);
            
            tercerosItemDialog.setVisible(true);
        }catch (Exception ex){
            Logger.getLogger(TercerosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Pulsar botón eliminar con fila seleccionada
    private void actionDel(){
        int selectedRow = view.getJTableElemento().getSelectedRow();
        if(selectedRow==-1){
            return;
        }
        SicatpersoEntity sicatperso= (SicatpersoEntity)view.getJTableElemento().getValueAt(selectedRow, -1);
        
        int res = JOptionPane.showOptionDialog(view,
            "¿Eliminar la persona con ID: "+sicatperso.getId()+" ?",
            "Salir",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,     
            new Object[]{"Si", "No"},  
            "Si"); //default button title
        if(res==0) {        
            TercerosRC.delete(sicatperso);
            JOptionPane.showMessageDialog(view, "ID con fecha de baja");

            //Recargar tabla
            resetFields();
            search();
        }
    }
    
    //Pulsar botón salir
    private void actionSalir(){
        view.dispose();
    }
    
    //Pulsar botón cancelar en dialog
    private void actionCanc(){
        resetFieldsDialog();
        tercerosItemDialog.dispose();
    }
    
    private void actionSelectRow() {
        int selectedRow = view.getJTableElemento().getSelectedRow();
        if(selectedRow==-1) return;
        SicatpersoEntity sicatperso= (SicatpersoEntity)view.getJTableElemento().getValueAt(selectedRow, -1);
        
        this.loadTextFields(sicatperso);
        setBotonEnabled(true);
    }
    
    //
    private void loadTextFields(SicatpersoEntity sicatperso){
        if(sicatperso!=null){
            view.getJTextFieldSearch().setText(sicatperso.getId() != null?sicatperso.getId():"");
        }else{
            view.getJTextFieldSearch().setText("");        
        }
    }
    
    //Ventana dialog para comprobar que haya datos y los recoja
    private SicatpersoEntity createObjectFromTextfields(){
        try {
            if (tercerosItemDialog.getJTextFieldNIF().getText().isEmpty()
                    || tercerosItemDialog.getJTextFieldApe1().getText().isEmpty()) {
                JOptionPane.showMessageDialog(tercerosItemDialog, "Rellene NIF y Primer Apellido");
                return null;
            }
            
            String NIF = tercerosItemDialog.getJTextFieldNIF().getText();
            String PApellido = tercerosItemDialog.getJTextFieldApe1().getText();
            String Nombre,SApellido,Correo,Direccion,Usuario;
            Integer Telefono,Municipio,CP,Provincia;
            
            if (tercerosItemDialog.getJTextFieldNombre().getText().isEmpty()) Nombre = null;
            else Nombre = tercerosItemDialog.getJTextFieldNombre().getText();
            if (tercerosItemDialog.getJTextFieldApe2().getText().isEmpty()) SApellido = null;
            else SApellido = tercerosItemDialog.getJTextFieldApe2().getText();
            if (tercerosItemDialog.getJTextFieldCorreo().getText().isEmpty()) Correo = null;
            else Correo = tercerosItemDialog.getJTextFieldCorreo().getText();
            if (tercerosItemDialog.getJTextFieldTelef().getText().isEmpty()) Telefono = null;
            else Telefono = Integer.parseInt(tercerosItemDialog.getJTextFieldTelef().getText());
            if (tercerosItemDialog.getJTextFieldMunic().getText().isEmpty()) Municipio = null;
            else Municipio = Integer.parseInt(tercerosItemDialog.getJTextFieldMunic().getText());
            if (tercerosItemDialog.getJTextFieldCPos().getText().isEmpty()) CP = null;
            else CP = Integer.parseInt(tercerosItemDialog.getJTextFieldCPos().getText());
            if (tercerosItemDialog.getJTextFieldProvin().getText().isEmpty()) Provincia = null;
            else Provincia = Integer.parseInt(tercerosItemDialog.getJTextFieldProvin().getText());
            if (tercerosItemDialog.getJTextFieldUser().getText().isEmpty()) Usuario = null;
            else Usuario = tercerosItemDialog.getJTextFieldUser().getText();
            if (tercerosItemDialog.getJTextFieldDirec().getText().isEmpty()) Direccion = null;
            else Direccion = tercerosItemDialog.getJTextFieldDirec().getText();
            
            SicatpersoEntity sicatperso = new SicatpersoEntity(NIF, PApellido, SApellido, Nombre, Direccion, Provincia, Municipio, CP, Telefono, Correo, Usuario);
            return sicatperso;
            
        } catch (NumberFormatException ex) {
            Logger.getLogger(TercerosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Dialog botón Confirmar
    private void actionOK(){
        if(crear){
            try {
                SicatpersoEntity sicat = this.createObjectFromTextfields();

                String dni = sicat.getId();
                char [] cad = new char[8];
                
                if(dni.length()!= 9){
                    JOptionPane.showMessageDialog(tercerosItemDialog, "NIF no válido (8 números y 1 letra mayúscula)");
                }
                else{
                    cad = dni.toCharArray();
                    char c = cad[8];

                    if (c >= 'A' && c <= 'Z') {
                        //Crearlo
                        TercerosRC.create(sicat);
                        JOptionPane.showMessageDialog(view, "ID: " + sicat.getId() + " con datos creado");

                        //Cerrar dialog
                        tercerosItemDialog.setVisible(false);

                        //Recargar tabla
                        resetFieldsDialog();
                        resetFields();
                        search();
                    }else {
                        JOptionPane.showMessageDialog(tercerosItemDialog, "NIF no válido (8 números y 1 letra mayúscula)");
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(TercerosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        else{
            SicatpersoEntity sicat = this.createObjectFromTextfields();
            TercerosRC.update(sicat);
            
            JOptionPane.showMessageDialog(view, "ID: "+sicat.getId()+" con datos actualizado");

            //Cerrar dialog
            tercerosItemDialog.setVisible(false);

            //Recargar tabla
            resetFieldsDialog();
            resetFields();
            search();
        }
    }

    public static void main(String args[]) {
        TercerosController tercerosController = new TercerosController();
        tercerosController.initController();
        tercerosController.initControllerDialog();
        tercerosController.displayView();
    }
}
