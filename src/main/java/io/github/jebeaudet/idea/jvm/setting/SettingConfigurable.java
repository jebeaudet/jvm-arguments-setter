package io.github.jebeaudet.idea.jvm.setting;

import javax.swing.JComponent;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.options.Configurable;

import io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence;

/**
 * @author Lucky-maxinchun
 * @author jebeaudet
 * @date 2021/7/14 13:56
 */
public class SettingConfigurable implements Configurable
{
    private final SettingForm settingForm;

    public SettingConfigurable()
    {
        this.settingForm = new SettingForm();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName()
    {
        return "JVM Arguments Setter";
    }

    @Nullable
    @Override
    public JComponent createComponent()
    {
        return settingForm.mainPanel;
    }

    @Override
    public boolean isModified()
    {
        return !StringUtils.equals(JVMArgumentsSetterPersistence.getJvmParameterList(),
                                   settingForm.getJvmParameterTableText());
    }

    @Override
    public void apply()
    {
        JVMArgumentsSetterPersistence.setJvmParameter(settingForm.getJvmParameterText(), settingForm.getTestJvmParameterText());
        JVMArgumentsSetterPersistence.setJvmParameterList(settingForm.getJvmParameterTableText());
    }

    @Override
    public void reset()
    {
        settingForm.setJvmParameterTableText(JVMArgumentsSetterPersistence.getJvmParameterList());
    }
}
