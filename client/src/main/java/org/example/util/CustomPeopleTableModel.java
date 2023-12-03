package org.example.util;

import org.example.dto.PersonDTO;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class CustomPeopleTableModel extends AbstractTableModel {

    private List<PersonDTO> data;
    private String[] columnNames;

    public CustomPeopleTableModel(List<PersonDTO> data, String[] columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PersonDTO person = data.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return person.getPassportNumber();
            }
            case 1 -> {
                return person.getFirstName();
            }
            case 2 -> {
                return person.getLastName();
            }
            case 3 -> {
                return person.getBirthDate();
            }
            case 4 -> {
                return person.getNationality();
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0, 1, 2, 4 -> {
                return String.class;
            }
            case 3 -> {
                return Date.class;
            }
            default -> {
                return Object.class;
            }
        }
    }
}
