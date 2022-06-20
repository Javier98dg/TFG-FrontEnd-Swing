package com.frontswing.client.controller;

import com.frontswing.client.data.entity.SicatpersoEntity;
import com.frontswing.client.views.terceros.TercerosFrameGUI;
import com.frontswing.client.data.tablemodel.SicatpersoTableModel;
import com.frontswing.client.data.service.TercerosRC;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author jjdg46
 */
public class TercerosGUIController {

    public TercerosFrameGUI guiView;
    private TercerosDialogController dialogController;

    public void initController() {
        guiView = new TercerosFrameGUI();

        //Cargar elementos
        setBotonEnabled(false);
        //SicatpersoEntity[] sicatperso = TercerosRC.getAllPageable(1,10);
        loadTable2(new SicatpersoEntity[0]);

        guiView.getJTableElemento().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Muestra todos los datos en la tabla
        SicatpersoEntity[] sicatperso = TercerosRC.getAll();
        loadTable2(sicatperso);

        //Cargar eventos
        guiView.getJTextFieldSearch().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search();
            }
        });
        guiView.getJButtonSalir().addActionListener(e -> actionSalir());
        guiView.getJTableElemento().getSelectionModel().addListSelectionListener(tableSelectionEvent -> actionSelectRow());
        guiView.getJButtonAdd().addActionListener(e -> actionAdd());
        guiView.getJButtonUpdate().addActionListener(e -> actionUpdate());
        guiView.getJButtonDel().addActionListener(e -> actionDel());
    }

    public void displayView() {
        guiView.setVisible(true);
    }

    //Filtro NIF
    public void search() {
        String termSearched = guiView.getJTextFieldSearch().getText();

        if (termSearched.isEmpty()) {
            setBotonEnabled(false);
            //SicatpersoEntity[] sicatperso = TercerosRC.getAllPageable(1,3);
            SicatpersoEntity[] sicatperso = TercerosRC.getAll();
            loadTable2(new SicatpersoEntity[0]);

            loadTable2(sicatperso);
        } else {
            SicatpersoEntity sicatperso = TercerosRC.searchPersoById(termSearched);
            this.loadTable(sicatperso);
        }
        if (guiView.getJTableElemento().getSelectedRow() != -1) {
            setBotonEnabled(true);
        } else {
            setBotonEnabled(false);
            loadTextFields(null);
        }
    }

    public void resetFields() {
        guiView.getJTextFieldSearch().setText("");
    }

    //Si se ha seleccionado una fila de la tabla
    //Desbloquea boton Borrar y Actualizar
    private void setBotonEnabled(boolean enabled) {
        guiView.getJButtonDel().setEnabled(enabled);
        guiView.getJButtonUpdate().setEnabled(enabled);

        if (!enabled) {
            guiView.getJTextFieldSearch().setText("");
        }
    }

    //TODO
    private void loadTable2(SicatpersoEntity[] rows) {
        SicatpersoTableModel tableModel = new SicatpersoTableModel(Arrays.asList(rows));
        guiView.getJTableElemento().setModel(tableModel);

        if (rows.length > 0) {
            guiView.getJTableElemento().setEnabled(true);
        } else {
            guiView.getJTableElemento().setEnabled(false);
        }
    }

    //Carga tabla con las filas
    private void loadTable(SicatpersoEntity rows) {
        SicatpersoTableModel tableModel = new SicatpersoTableModel(Arrays.asList(rows));

        guiView.getJTableElemento().setModel(tableModel);
        guiView.getJTableElemento().setEnabled(true);
    }

    private void actionAdd() {
        dialogController = new TercerosDialogController(this);
        dialogController.actionAdd();
    }

    //Pulsar botón actualizar con fila seleccionada
    private void actionUpdate() {
        dialogController = new TercerosDialogController(this);
        try {
            int selectedRow = guiView.getJTableElemento().getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            SicatpersoEntity elemento = (SicatpersoEntity) guiView.getJTableElemento().getValueAt(selectedRow, -1);

            dialogController.actionUpdate(elemento);
        } catch (Exception ex) {
            Logger.getLogger(TercerosGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Pulsar botón eliminar con fila seleccionada
    private void actionDel() {
        int selectedRow = guiView.getJTableElemento().getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        SicatpersoEntity sicatperso = (SicatpersoEntity) guiView.getJTableElemento().getValueAt(selectedRow, -1);

        int res = JOptionPane.showOptionDialog(guiView,
                "¿Eliminar la persona con ID: " + sicatperso.getId() + " ?",
                "Salir",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Si", "No"},
                "Si"); //default button title
        if (res == 0) {
            TercerosRC.delete(sicatperso);
            JOptionPane.showMessageDialog(guiView, "ID con fecha de baja");

            //Recargar tabla
            resetFields();
            search();
        }
    }

    //Pulsar botón salir
    private void actionSalir() {
        guiView.dispose();
    }

    private void actionSelectRow() {
        int selectedRow = guiView.getJTableElemento().getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        SicatpersoEntity sicatperso = (SicatpersoEntity) guiView.getJTableElemento().getValueAt(selectedRow, -1);

        this.loadTextFields(sicatperso);
        setBotonEnabled(true);
    }

    //
    private void loadTextFields(SicatpersoEntity sicatperso) {
        if (sicatperso != null) {
            guiView.getJTextFieldSearch().setText(sicatperso.getId() != null ? sicatperso.getId() : "");
        } else {
            guiView.getJTextFieldSearch().setText("");
        }
    }

    public static void main(String args[]) {
        TercerosGUIController tercerosController = new TercerosGUIController();

        tercerosController.initController();
        //tercerosController.dialogCon.initControllerDialog();
        tercerosController.displayView();
    }
}
