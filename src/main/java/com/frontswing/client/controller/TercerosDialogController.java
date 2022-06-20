package com.frontswing.client.controller;

import com.frontswing.client.entity.SicatpersoEntity;
import com.frontswing.client.gui.TercerosItemDialog;
import com.frontswing.client.rest.TercerosRC;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jjdg46
 */
public class TercerosDialogController {

    private TercerosItemDialog itemDialog;
    private TercerosGUIController guiController;
    boolean crear;

    public TercerosDialogController(TercerosGUIController controllerGUIController) {
        this.guiController = controllerGUIController;
        this.initControllerDialog();
    }

    public void initControllerDialog() {
        itemDialog = new TercerosItemDialog(guiController.guiView, true);
        itemDialog.getJButtonOK().addActionListener(e -> actionOK());
        itemDialog.getJButtonCanc().addActionListener(e -> actionCanc());
    }

    public void noViewDialog() {
        itemDialog.setVisible(false);
    }

    public void resetFieldsDialog() {
        itemDialog.getJTextFieldApe1().setText("");
        itemDialog.getJTextFieldApe2().setText("");
        itemDialog.getJTextFieldCPos().setText("");
        itemDialog.getJTextFieldCorreo().setText("");
        itemDialog.getJTextFieldFAct().setText("");
        itemDialog.getJTextFieldMunic().setText("");
        itemDialog.getJTextFieldNIF().setText("");
        itemDialog.getJTextFieldNombre().setText("");
        itemDialog.getJTextFieldProvin().setText("");
        itemDialog.getJTextFieldTelef().setText("");
        itemDialog.getJTextFieldUser().setText("");
        itemDialog.getJTextFieldDirec().setText("");
    }

    //Pulsar botón crear
    public void actionAdd() {
        try {
            crear = true;
            itemDialog.getJTextFieldNIF().setEditable(true);
            itemDialog.getJTextFieldUser().setEditable(true);
            itemDialog.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(TercerosGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Pulsar botón actualizar con fila seleccionada
    public void actionUpdate(SicatpersoEntity elemento) {
        try {
            crear = false;
            itemDialog.getJTextFieldNIF().setEditable(false);
            itemDialog.getJTextFieldUser().setEditable(false);

            String NIF = elemento.getId();
            String Usuario = elemento.getUsuario();
            String PApellido = elemento.getPrimerApe();
            String Nombre, SApellido, Correo, Direccion;
            Integer Telefono, Municipio, CP, Provincia;
            String TelefonoAux, MunicipioAux, CPAux, ProvinciaAux;

            if (elemento.getTelefono() == null) {
                TelefonoAux = "";
                itemDialog.getJTextFieldTelef().setText(TelefonoAux);
            } else {
                Telefono = elemento.getTelefono();
                itemDialog.getJTextFieldTelef().setText(Integer.toString(Telefono));
            }
            if (elemento.getCodMunicipio() == null) {
                MunicipioAux = "";
                itemDialog.getJTextFieldMunic().setText(MunicipioAux);
            } else {
                Municipio = elemento.getCodMunicipio();
                itemDialog.getJTextFieldMunic().setText(Integer.toString(Municipio));
            }
            if (elemento.getCodPostal() == null) {
                CPAux = "";
                itemDialog.getJTextFieldCPos().setText(CPAux);
            } else {
                CP = elemento.getCodPostal();
                itemDialog.getJTextFieldCPos().setText(Integer.toString(CP));
            }
            if (elemento.getCodProvincia() == null) {
                ProvinciaAux = "";
                itemDialog.getJTextFieldProvin().setText(ProvinciaAux);
            } else {
                Provincia = elemento.getCodProvincia();
                itemDialog.getJTextFieldProvin().setText(Integer.toString(Provincia));
            }

            if (elemento.getNombre() == null) {
                Nombre = "";
            } else {
                Nombre = elemento.getNombre();
            }
            if (elemento.getSegundoApe() == null) {
                SApellido = "";
            } else {
                SApellido = elemento.getSegundoApe();
            }
            if (elemento.getCorreoElec() == null) {
                Correo = "";
            } else {
                Correo = elemento.getCorreoElec();
            }
            if (elemento.getDireccion() == null) {
                Direccion = "";
            } else {
                Direccion = elemento.getDireccion();
            }

            LocalDateTime fecAc = elemento.getFechaActualizacion();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String FechaAct = fecAc.format(dateTimeFormatter);

            itemDialog.getJTextFieldNIF().setText(NIF);
            itemDialog.getJTextFieldNombre().setText(Nombre);
            itemDialog.getJTextFieldApe1().setText(PApellido);
            itemDialog.getJTextFieldApe2().setText(SApellido);
            itemDialog.getJTextFieldCorreo().setText(Correo);
            itemDialog.getJTextFieldFAct().setText(FechaAct);
            itemDialog.getJTextFieldDirec().setText(Direccion);
            itemDialog.getJTextFieldUser().setText(Usuario);

            itemDialog.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(TercerosGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Pulsar botón cancelar en dialog
    private void actionCanc() {
        resetFieldsDialog();
        itemDialog.dispose();
    }

    //Ventana dialog para comprobar que haya datos y los recoja
    private SicatpersoEntity createObjectFromTextfields() {
        try {
            if (itemDialog.getJTextFieldNIF().getText().isEmpty()
                    || itemDialog.getJTextFieldApe1().getText().isEmpty()
                    || itemDialog.getJTextFieldUser().getText().isEmpty()) {
                JOptionPane.showMessageDialog(itemDialog, "Rellene NIF, Primer Apellido y Usuario");
                return null;
            }

            String NIF = itemDialog.getJTextFieldNIF().getText();
            String PApellido = itemDialog.getJTextFieldApe1().getText();
            String Nombre, SApellido, Correo, Direccion, Usuario;
            Integer Telefono, Municipio, CP, Provincia;

            if (itemDialog.getJTextFieldNombre().getText().isEmpty()) {
                Nombre = null;
            } else {
                Nombre = itemDialog.getJTextFieldNombre().getText();
            }
            if (itemDialog.getJTextFieldApe2().getText().isEmpty()) {
                SApellido = null;
            } else {
                SApellido = itemDialog.getJTextFieldApe2().getText();
            }
            if (itemDialog.getJTextFieldCorreo().getText().isEmpty()) {
                Correo = null;
            } else {
                Correo = itemDialog.getJTextFieldCorreo().getText();
            }
            if (itemDialog.getJTextFieldTelef().getText().isEmpty()) {
                Telefono = null;
            } else {
                Telefono = Integer.parseInt(itemDialog.getJTextFieldTelef().getText());
            }
            if (itemDialog.getJTextFieldMunic().getText().isEmpty()) {
                Municipio = null;
            } else {
                Municipio = Integer.parseInt(itemDialog.getJTextFieldMunic().getText());
            }
            if (itemDialog.getJTextFieldCPos().getText().isEmpty()) {
                CP = null;
            } else {
                CP = Integer.parseInt(itemDialog.getJTextFieldCPos().getText());
            }
            if (itemDialog.getJTextFieldProvin().getText().isEmpty()) {
                Provincia = null;
            } else {
                Provincia = Integer.parseInt(itemDialog.getJTextFieldProvin().getText());
            }
            if (itemDialog.getJTextFieldUser().getText().isEmpty()) {
                Usuario = null;
            } else {
                Usuario = itemDialog.getJTextFieldUser().getText();
            }
            if (itemDialog.getJTextFieldDirec().getText().isEmpty()) {
                Direccion = null;
            } else {
                Direccion = itemDialog.getJTextFieldDirec().getText();
            }

            SicatpersoEntity sicatperso = new SicatpersoEntity(NIF, PApellido, SApellido, Nombre, Direccion, Provincia, Municipio, CP, Telefono, Correo, Usuario);
            return sicatperso;

        } catch (NumberFormatException ex) {
            Logger.getLogger(TercerosGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Dialog botón Confirmar
    private void actionOK() {
        if (crear) {
            try {
                SicatpersoEntity sicat = this.createObjectFromTextfields();

                String dni = sicat.getId();
                char[] cad = new char[8];

                if (dni.length() != 9) {
                    JOptionPane.showMessageDialog(itemDialog, "NIF no válido (8 números y 1 letra mayúscula)");
                } else {
                    cad = dni.toCharArray();
                    char c = cad[8];

                    if (c >= 'A' && c <= 'Z') {
                        //Crearlo
                        TercerosRC.create(sicat);
                        JOptionPane.showMessageDialog(guiController.guiView, "ID: " + sicat.getId() + " con datos creado");

                        //Cerrar dialog
                        itemDialog.setVisible(false);

                        //Recargar tabla
                        resetFieldsDialog();
                        guiController.resetFields();
                        guiController.search();
                    } else {
                        JOptionPane.showMessageDialog(itemDialog, "NIF no válido (8 números y 1 letra mayúscula)");
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(TercerosGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            SicatpersoEntity sicat = this.createObjectFromTextfields();
            TercerosRC.update(sicat);

            JOptionPane.showMessageDialog(guiController.guiView, "ID: " + sicat.getId() + " con datos actualizado");

            //Cerrar dialog
            itemDialog.setVisible(false);

            //Recargar tabla
            resetFieldsDialog();
            guiController.resetFields();
            guiController.search();
        }
    }
}