package org.example.util;

import org.example.dto.VisaDTO;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class CustomVisasTableModel extends AbstractTableModel {

    private List<VisaDTO> data;
    private String[] columnNames;

    public CustomVisasTableModel(List<VisaDTO> data, String[] columnNames) {
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
        VisaDTO visa = data.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return visa.getId();
            }
            case 1 -> {
                return visa.getIssueDate();
            }
            case 2 -> {
                return visa.getCountries();
            }
            case 3 -> {
                return visa.getType();
            }
            case 4 -> {
                return visa.getValidityPeriod();
            }
            case 5 -> {
                if (visa.getPerson() != null) {
                    return visa.getPerson().getPassportNumber();
                }
                return "";
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
            case 0 -> {
                return Long.class;
            }
            case 1 -> {
                return Date.class;
            }
            case 2, 5 -> {
                return String.class;
            }
            case 3 -> {
                return Character.class;
            }
            case 4 -> {
                return Double.class;
            }
            default -> {
                return Object.class;
            }
        }
    }
}
