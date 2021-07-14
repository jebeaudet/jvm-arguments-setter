package com.wtyt.lucky.idea.jvm.setting;

import com.intellij.ui.TableUtil;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.JBTable;
import com.intellij.uiDesigner.core.GridConstraints;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述
 *
 * @author Lucky-maxinchun
 * @date 2021/7/14 14:08
 */
public class SettingForm {

    public JPanel mainPanel;
    private JTextField jvmParameterText;
    private JPanel decorationLayoutPanel;


    private MyJvmTableModel dataModel = new MyJvmTableModel();
    private TableModelListener tableModelListener = e -> {
        String jvmParameter = dataModel.list.stream()
                .filter(cs -> Objects.equals(true, cs[0]))
                .filter(cs -> StringUtils.isNotEmpty(String.valueOf(cs[1])))
                .map(cs -> {
                    String c2 = String.valueOf(cs[2]);
                    if (StringUtils.isEmpty(c2)) {
                        return String.valueOf(cs[1]);
                    }
                    return !StringUtils.containsWhitespace(c2)
                            ? "-D" + cs[1] + "=" + c2
                            : "\"-D" + cs[1] + "=" + c2 + "\"";
                })
                .collect(Collectors.joining(" "));
        jvmParameterText.setText(jvmParameter);
        jvmParameterText.setToolTipText(jvmParameter);
    };

    public SettingForm() {
        init();
    }

    private void init() {
        dataModel.addTableModelListener(tableModelListener);

        JBTable jbTable = new JBTable(dataModel);
        jbTable.getColumnModel().getColumn(0).setMaxWidth(40);
        ToolbarDecorator decorationToolbar = ToolbarDecorator.createDecorator(jbTable);

        decorationToolbar.setAddAction(button -> {
            EventQueue.invokeLater(dataModel::addRow);
        });
        decorationToolbar.setRemoveAction(button -> {
            EventQueue.invokeLater(() -> {
                TableUtil.removeSelectedItems(jbTable);
            });
        });
        decorationLayoutPanel.add(decorationToolbar.createPanel(), BorderLayout.CENTER);

        // 生成快捷按钮
        // generateButton();
    }


    public String getJvmParameterTableText() {
        return dataModel.list.stream()
                .flatMap(objects -> Stream.of(objects)
                        .map(o -> StringUtils.isEmpty(o.toString()) ? " " : o.toString()))
                .collect(Collectors.joining("@@@"));
    }

    public void setJvmParameterTableText(String jvmParameterTableText) {
        String[] split = StringUtils.split(jvmParameterTableText, "@@@");
        if (split == null || split.length % 3 != 0) {
            return;
        }

        dataModel.clear();
        for (int i = 0; i < split.length; i = i + 3) {
            dataModel.addRow(BooleanUtils.toBoolean(split[i]), StringUtils.trimToEmpty(split[i + 1]),
                    StringUtils.trimToEmpty(split[i + 2]));
        }
    }

    public String getJvmParameterText() {
        return jvmParameterText.getText();
    }
}
