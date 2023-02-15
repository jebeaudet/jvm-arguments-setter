package io.github.jebeaudet.idea.jvm.util;

import java.util.Optional;

import com.intellij.ide.util.PropertiesComponent;
import io.github.jebeaudet.idea.jvm.setting.SettingForm;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * https://plugins.jetbrains.com/docs/intellij/persisting-state-of-components.html
 *
 * @author Lucky-maxinchun
 * @author jebeaudet
 * @date 2021/7/14 14:42
 */
public final class JVMArgumentsSetterPersistence
{
    private static final String KEY_JVM_PARAMETER = "io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence.jvmParameter";
    private static final String TEST_KEY_JVM_PARAMETER = "io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence.testJvmParameter";

    private static final String KEY_PLUGIN_ENABLED = "io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence.pluginEnabled";

    private static final String KEY_JVM_PARAMETER_LIST = "io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence.jvmParameterList";
    private static final String KEY_JVM_PARAMETER_LIST_V2 = "io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence.jvmParameterListV2";

    private static final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

    private JVMArgumentsSetterPersistence()
    {
    }

    public static String getJvmArguments(boolean isTestRun)
    {
        if(isConfigNotMigrated()) {
            return propertiesComponent.getValue(KEY_JVM_PARAMETER);
        }
        return isTestRun ? propertiesComponent.getValue(TEST_KEY_JVM_PARAMETER) : propertiesComponent.getValue(KEY_JVM_PARAMETER);
    }

    public static void setJvmParameter(String jvmParameters, String testJvmParameters)
    {
        propertiesComponent.setValue(KEY_JVM_PARAMETER, jvmParameters);
        propertiesComponent.setValue(TEST_KEY_JVM_PARAMETER, testJvmParameters);
    }

    public static boolean getIsEnabled()
    {
        return propertiesComponent.getBoolean(KEY_PLUGIN_ENABLED, true);
    }

    public static void setEnabled(boolean enabled)
    {
         propertiesComponent.setValue(KEY_PLUGIN_ENABLED, Boolean.toString(enabled));
    }

    public static String getJvmParameterList()
    {
        String parametersV1 = propertiesComponent.getValue(KEY_JVM_PARAMETER_LIST);
        String parametersV2 = propertiesComponent.getValue(KEY_JVM_PARAMETER_LIST_V2);
        if (parametersV2 == null && parametersV1 != null) {
            parametersV2 = SettingForm.migrateToV2(parametersV1);
            if (parametersV2 != null) {
                propertiesComponent.unsetValue(KEY_JVM_PARAMETER_LIST);
                propertiesComponent.setValue(KEY_JVM_PARAMETER_LIST_V2, parametersV2);
            }
        }
        return parametersV2;
    }

    public static void setJvmParameterList(String jvmParameterList)
    {
        propertiesComponent.setValue(KEY_JVM_PARAMETER_LIST_V2, jvmParameterList);
    }

    private static void isConfigNotMigrated()
    {
        String parametersV1 = propertiesComponent.getValue(KEY_JVM_PARAMETER_LIST);
        String parametersV2 = propertiesComponent.getValue(KEY_JVM_PARAMETER_LIST_V2);
        return parametersV2 == null && parametersV1 != null;
    }
}
