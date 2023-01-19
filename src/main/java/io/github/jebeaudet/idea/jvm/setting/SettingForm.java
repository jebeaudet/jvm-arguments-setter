package io.github.jebeaudet.idea.jvm.setting;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;
import javax.swing.event.TableModelListener;

import io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence;
import org.apache.commons.lang3.ArrayUtils;
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
public class SettingForm {
    private static final Logger logger = Logger.getInstance(SettingForm.class);
    private static final String SEPARATOR = "<@!<\\@";
    private static final int MIN_MAX_WIDTH = 60;

    public JPanel mainPanel;
    private JPanel decorationLayoutPanel;
    private JTextField jvmArgsTestTextField;
    private JTextField jvmArgsTextField;
    JCheckBox enabledCheckBox;
    private JVMArgumentsTableModel dataModel = new JVMArgumentsTableModel();

    private TableModelListener tableModelListener = event -> {
        String jvmParameter = formatTableToJVMArguments(args -> true);
        String testJvmParameter = formatTableToJVMArguments(args -> Boolean.TRUE.equals(args[3]));
        jvmArgsTextField.setText(jvmParameter);
        jvmArgsTestTextField.setText(testJvmParameter);
    };

    private String formatTableToJVMArguments(Predicate<Object[]> additionalPredicate) {
        return dataModel.getList()
                .stream()
                .filter(args -> Boolean.TRUE.equals(args[0]))
                .filter(additionalPredicate)
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
    }

    public SettingForm() {
        init();
    }

    private void init() {
        dataModel.addTableModelListener(tableModelListener);

        JBTable jbTable = new JBTable(dataModel);
        jbTable.getColumnModel().getColumn(0).setMaxWidth(MIN_MAX_WIDTH);
        jbTable.getColumnModel().getColumn(0).setMinWidth(MIN_MAX_WIDTH);
        jbTable.getColumnModel().getColumn(3).setMaxWidth(MIN_MAX_WIDTH);
        jbTable.getColumnModel().getColumn(3).setMinWidth(MIN_MAX_WIDTH);
        jbTable.getTableHeader().setReorderingAllowed(false);

        ToolbarDecorator decorationToolbar = ToolbarDecorator.createDecorator(jbTable);

        decorationToolbar.setAddAction(button -> EventQueue.invokeLater(dataModel::addRow));
        decorationToolbar.setRemoveAction(button -> EventQueue.invokeLater(() -> TableUtil.removeSelectedItems(jbTable)));
        decorationLayoutPanel.add(decorationToolbar.createPanel(), BorderLayout.CENTER);
    }

    public String getJvmParameterTableText() {
        return dataModel.getList()
                .stream()
                .filter(args -> StringUtils.isNotBlank(String.valueOf(args[1])))
                .flatMap(objects -> Stream.of(objects)
                        .map(object -> StringUtils.defaultIfBlank(object.toString(),
                                StringUtils.SPACE)))
                .collect(Collectors.joining(SEPARATOR));
    }

    public void setJvmParameterTableText(String jvmParameterTableText) {
        String[] split = StringUtils.splitByWholeSeparator(jvmParameterTableText, SEPARATOR);
        if (split == null) {
            return;
        }
        if (split.length % 4 != 0) {
            logger.error(String.format("Couldn't not parse the string '%s' to a proper representation of the table. Resultant array '%s'.",
                    jvmParameterTableText,
                    Arrays.toString(split)));
            return;
        }

        dataModel.clear();
        for (int i = 0; i < split.length; i = i + 4) {
            dataModel.addRow(BooleanUtils.toBoolean(split[i]),
                    StringUtils.trimToEmpty(split[i + 1]),
                    StringUtils.trimToEmpty(split[i + 2]),
                    BooleanUtils.toBoolean(split[i + 3]));
        }
    }

    public String getJvmParameterText() {
        return jvmArgsTextField.getText();
    }
    public String getTestJvmParameterText() {
        return jvmArgsTestTextField.getText();
    }

    public static String migrateToV2(String parametersV1) {
        logger.info("Migration JVM Arguments setter to V2");
        String[] split = StringUtils.splitByWholeSeparator(parametersV1, SEPARATOR);
        if (split == null) {
            logger.info("Nothing to migrate.");
            return null;
        }
        if (split.length % 3 != 0) {
            logger.error(String.format("Couldn't migrate the string '%s' to v2! Resultant array '%s'.",
                    parametersV1,
                    Arrays.toString(split)));
            return null;
        }

        int numberOfArguments = split.length / 3;
        for (int i = 1; i <= numberOfArguments; i++) {
            split = ArrayUtils.insert(i * 3 + (i - 1), split, Boolean.TRUE.toString());
        }

        String parametersV2 = Stream.of(split)
                .flatMap(objects -> Stream.of(objects)
                        .map(object -> StringUtils.defaultIfBlank(object.toString(),
                                StringUtils.SPACE)))
                .collect(Collectors.joining(SEPARATOR));
        logger.info("Migration successful!");
        return parametersV2;
    }
}
