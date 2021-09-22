package io.github.jebeaudet.idea.jvm.setting;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.TableUtil;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.JBTable;

/**
 * @author Lucky-maxinchun
 * @author jebeaudet
 * @date 2021/7/14 14:08
 */
public class SettingForm
{
    private static final Logger logger = Logger.getInstance(SettingForm.class);
    private static final String SEPARATOR = "<@!<\\@";
    private static final int MAX_WIDTH = 40;

    public JPanel mainPanel;
    private JTextField jvmParameterText;
    private JPanel decorationLayoutPanel;
    private JVMArgumentsTableModel dataModel = new JVMArgumentsTableModel();

    private TableModelListener tableModelListener = event -> {
        String jvmParameter = dataModel.getList()
                                       .stream()
                                       .filter(args -> Boolean.TRUE.equals(args[0]))
                                       .filter(args -> StringUtils.isNotEmpty(String.valueOf(args[1])))
                                       .map(args -> {
                                           String key = args[1].toString();
                                           String value = String.valueOf(args[2]);
                                           if (StringUtils.isEmpty(value)) {
                                               return key;

                                           }
                                           if (key.startsWith("--") || key.startsWith("-")) {
                                               return key + " " + value;
                                           }

                                           String argument = "-D" + key + "=" + value;
                                           return StringUtils.containsWhitespace(value) ? String.format("\"%s\"",
                                                                                                        argument)
                                                                                        : argument;
                                       })
                                       .collect(Collectors.joining(" "));
        jvmParameterText.setText(jvmParameter);
        jvmParameterText.setToolTipText(jvmParameter);
    };

    public SettingForm()
    {
        init();
    }

    private void init()
    {
        dataModel.addTableModelListener(tableModelListener);

        JBTable jbTable = new JBTable(dataModel);
        jbTable.getColumnModel().getColumn(0).setMaxWidth(MAX_WIDTH);
        ToolbarDecorator decorationToolbar = ToolbarDecorator.createDecorator(jbTable);

        decorationToolbar.setAddAction(button -> EventQueue.invokeLater(dataModel::addRow));
        decorationToolbar.setRemoveAction(button -> EventQueue.invokeLater(() -> TableUtil.removeSelectedItems(jbTable)));
        decorationLayoutPanel.add(decorationToolbar.createPanel(), BorderLayout.CENTER);
    }

    public String getJvmParameterTableText()
    {
        return dataModel.getList()
                        .stream()
                        .filter(args -> StringUtils.isNotBlank(String.valueOf(args[1])))
                        .flatMap(objects -> Stream.of(objects)
                                                  .map(object -> StringUtils.defaultIfBlank(object.toString(),
                                                                                            StringUtils.SPACE)))
                        .collect(Collectors.joining(SEPARATOR));
    }

    public void setJvmParameterTableText(String jvmParameterTableText)
    {
        String[] split = StringUtils.splitByWholeSeparator(jvmParameterTableText, SEPARATOR);
        if (split == null) {
            return;
        }
        if (split.length % 3 != 0) {
            logger.error(String.format("Couln't not parse the string '%s' to a proper representation of the table. Resultant array '%s'.",
                                       jvmParameterTableText,
                                       Arrays.toString(split)));
            return;
        }

        dataModel.clear();
        for (int i = 0; i < split.length; i = i + 3) {
            dataModel.addRow(BooleanUtils.toBoolean(split[i]),
                             StringUtils.trimToEmpty(split[i + 1]),
                             StringUtils.trimToEmpty(split[i + 2]));
        }
    }

    public String getJvmParameterText()
    {
        return jvmParameterText.getText();
    }
}
