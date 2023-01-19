package io.github.jebeaudet.idea.jvm.setting;

import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;

public class ToolTipJTable extends JBTable {

    public ToolTipJTable(TableModel model) {
        super(model, null);
    }

    public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);

        try {
            if(rowIndex != 0){
                tip = "ALLOOOO";
            }
        } catch (RuntimeException ignored) {
        }

        return tip;
    }
}
