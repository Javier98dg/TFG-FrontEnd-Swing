package com.frontswing.client.gui.tablemodel;

import com.frontswing.client.entity.SicatpersoEntity;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author jjdg46
 */
@Data
@AllArgsConstructor
public class SicatpersoTableModel  extends AbstractTableModel {

    private List<SicatpersoEntity> rows;
    private final String[] columnNames = {"NIF","Fecha Actualización","Fecha Baja","Nombre",
        "Primer Apellido","Segundo Apellido","Correo Electrónico","Teléfono","Municipio","Código Postal","Provincia"};

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == -1) {
            return null;
        }
        SicatpersoEntity row = rows.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getId();
            case 1:
                return row.getFechaActualizacion();
            case 2:
                return row.getFechaBaja();
            case 3:
                return row.getNombre();
            case 4:
                return row.getPrimerApe();
            case 5:
                return row.getSegundoApe();
            case 6:
                return row.getCorreoElec();
            case 7:
                return row.getTelefono();
            case 8:
                return row.getCodMunicipio();
            case 9:
                return row.getCodPostal();
            case 10:
                return row.getCodProvincia();
            case -1:
                return row;
        }
        return null;
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        SicatpersoEntity row = rows.get(rowIndex);
        switch (columnIndex) {
            case 0:
                row.setId((String) value);
                break;  
//            case 1:
//                row.setFechaActualizacion(LocalDateTime.MIN);
//                break;        
//            case 2:
//                row.setFechaBaja(LocalDateTime.MAX);
//                break;
            case 3:
                row.setNombre((String) value);
                break;
            case 4:
                row.setPrimerApe((String) value);
                break;
            case 5:
                row.setSegundoApe((String) value);
                break;
            case 6:
                row.setCorreoElec((String) value);
                break;
            case 7:
                row.setTelefono((Integer) value);
                break;
            case 8:
                row.setCodMunicipio((Integer) value);
                break;
            case 9:
                row.setCodPostal((Integer) value);
                break;
            case 10:
                row.setCodProvincia((Integer) value);
                break;
            case -1:
                row=(SicatpersoEntity)value;
                break;
        }        
        rows.set(rowIndex, row);
    }   
    
    @Override
    public int getRowCount() {
        return rows.size();
    }
    
    /*@Override
    public Class getColumnClass(int column) {
        return (getValueAt(0, column).getClass());
    }*/

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }    
}
