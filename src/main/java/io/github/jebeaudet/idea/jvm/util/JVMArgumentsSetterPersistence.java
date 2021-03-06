package io.github.jebeaudet.idea.jvm.util;

import java.util.Optional;

import com.intellij.ide.util.PropertiesComponent;

/**
 * https://plugins.jetbrains.com/docs/intellij/persisting-state-of-components.html
 *
 * @author Lucky-maxinchun
 * @author jebeaudet
 * @date 2021/7/14 14:42
 */
public final class JVMArgumentsSetterPersistence
{
    private static final String DEPRECATED_KEY_JVM_PARAMETER = "com.wtyt.lucky.idea.jvm.util.AppConfigKit.jvmParameter";
    private static final String DEPRECATED_KEY_JVM_PARAMETER_LIST = "com.wtyt.lucky.idea.jvm.util.AppConfigKit.jvmParameterList";

    private static final String KEY_JVM_PARAMETER = "io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence.jvmParameter";
    private static final String KEY_JVM_PARAMETER_LIST = "io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence.jvmParameterList";

    private static final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

    private JVMArgumentsSetterPersistence()
    {
    }

    public static String getJvmArguments()
    {
        return Optional.ofNullable(propertiesComponent.getValue(KEY_JVM_PARAMETER))
                       .orElseGet(() -> propertiesComponent.getValue(DEPRECATED_KEY_JVM_PARAMETER));
    }

    public static void setJvmParameter(String jvmParameter)
    {
        propertiesComponent.setValue(KEY_JVM_PARAMETER, jvmParameter);
    }

    public static String getJvmParameterList()
    {
        return Optional.ofNullable(propertiesComponent.getValue(KEY_JVM_PARAMETER_LIST))
                       .orElseGet(() -> propertiesComponent.getValue(DEPRECATED_KEY_JVM_PARAMETER_LIST));
    }

    public static void setJvmParameterList(String jvmParameterList)
    {
        propertiesComponent.setValue(KEY_JVM_PARAMETER_LIST, jvmParameterList);
    }
}
