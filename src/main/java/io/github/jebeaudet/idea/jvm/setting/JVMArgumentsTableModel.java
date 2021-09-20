package io.github.jebeaudet.idea.jvm.setting;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.intellij.util.ui.EditableModel;

/**
 * @author huzunrong
 * @author jebeaudet
 * @since 1.0
 */
public class JVMArgumentsTableModel extends AbstractTableModel implements EditableModel
{
    private static final long serialVersionUID = 6183423505608059672L;

    private List<Object[]> list = new ArrayList<>();

    private String[] head = { "", "Name", "Value" };

    private Class<?>[] typeArray = { Boolean.class, Object.class, Object.class };

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

    public void addRow(boolean enable, String name, String value)
    {
        list.add(new Object[] { enable, name, value });
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
        list.add(new Object[] { true, "", "" });
        fireTableRowsInserted(getRowCount() - 1, getRowCount());
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
}