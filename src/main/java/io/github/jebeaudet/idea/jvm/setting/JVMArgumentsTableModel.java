package io.github.jebeaudet.idea.jvm.setting;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import com.intellij.openapi.util.NlsContexts;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.EditableModel;
import com.intellij.util.ui.SortableColumnModel;
import org.jetbrains.annotations.Nullable;

/**
 * @author huzunrong
 * @author jebeaudet
 * @since 1.0
 */
public class JVMArgumentsTableModel extends AbstractTableModel implements EditableModel, SortableColumnModel
{
    private static final long serialVersionUID = 6183423505608059672L;

    private List<Object[]> list = new ArrayList<>();

    private String[] head = { "Enabled", "Name", "Value", "Tests" };

    private Class<?>[] typeArray = { Boolean.class, Object.class, Object.class, Boolean.class };

    private ColumnInfo[] columnInfos = new ColumnInfo[]{
            new ToolTipColumnInfo("Enabled", "Enable/Disable the argument"),
            new ToolTipColumnInfo("Name", "Argument key"),
            new ToolTipColumnInfo("Value", "Argument value (can be blank for non JVM arguments)"),
            new ToolTipColumnInfo("Tests", "Whether the argument is added to test run configurations")
    };


    public List<Object[]> getList()
    {
        return list;
    }

    @Override
    public int getRowCount()
    {
        return list.size();
    }

    @Override
    public int getColumnCount()
    {
        return head.length;
    }

    @Override
    public String getColumnName(int column)
    {
        return head[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return list.get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        list.get(rowIndex)[columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return typeArray[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

    public void addRow(boolean enable, String name, String value, boolean testEnabled)
    {
        list.add(new Object[] { enable, name, value,testEnabled });
        fireTableRowsInserted(getRowCount() - 1, getRowCount());
    }

    public void clear()
    {
        list.clear();
        fireTableDataChanged();
    }

    @Override
    public void addRow()
    {
        addRow(true, "", "", true);
    }

    @Override
    public void exchangeRows(int oldIndex, int newIndex)
    {
        Object[] objects = list.get(oldIndex);
        list.set(oldIndex, list.get(newIndex));
        list.set(newIndex, objects);
        fireTableDataChanged();
    }

    @Override
    public boolean canExchangeRows(int oldIndex, int newIndex)
    {
        return true;
    }

    @Override
    public void removeRow(int idx)
    {
        list.remove(idx);
        fireTableRowsDeleted(0, getRowCount());
    }

    @Override
    public ColumnInfo[] getColumnInfos() {
        return columnInfos;
    }

    @Override
    public void setSortable(boolean aBoolean) {
    }

    @Override
    public boolean isSortable() {
        return false;
    }

    @Override
    public Object getRowValue(int row) {
        return null;
    }

    @Override
    public RowSorter.@Nullable SortKey getDefaultSortKey() {
        return null;
    }

    private static class ToolTipColumnInfo extends ColumnInfo {
        private final String toolTip;

        public ToolTipColumnInfo(String name, String toolTip) {
            super(name);
            this.toolTip=toolTip;
        }

        @Nullable
        public String getTooltipText() {
            return toolTip;
        }

        @Nullable
        @Override
        public Object valueOf(Object o) {
            return null;
        }
    }
}