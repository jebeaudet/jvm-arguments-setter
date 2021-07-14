package com.wtyt.lucky.idea.jvm.setting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.wtyt.lucky.idea.jvm.util.AppConfigKit;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 类描述
 *
 * @author Lucky-maxinchun
 * @date 2021/7/14 13:56
 */
public class SettingConfigurable implements Configurable {

    private final SettingForm settingForm;

    public SettingConfigurable() {
        System.out.println("click Global Jvm Parameter");
        this.settingForm = new SettingForm();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Global Jvm Parameter";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return settingForm.mainPanel;
    }

    @Override
    public boolean isModified() {
        return !StringUtils.equals(AppConfigKit.getJvmParameterList(), settingForm.getJvmParameterTableText());
    }

    @Override
    public void apply() throws ConfigurationException {
        AppConfigKit.setJvmParameter(settingForm.getJvmParameterText());
        AppConfigKit.setJvmParameterList(settingForm.getJvmParameterTableText());
    }

    @Override
    public void reset() {
        settingForm.setJvmParameterTableText(AppConfigKit.getJvmParameterList());
    }
}
