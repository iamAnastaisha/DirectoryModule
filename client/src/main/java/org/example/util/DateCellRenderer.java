package org.example.util;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;

public class DateCellRenderer extends DefaultTableCellRenderer {
    public DateCellRenderer() { super(); }

    @Override
    public void setValue(Object value) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        setText((value == null) ? "" : sdf.format(value));
    }
}