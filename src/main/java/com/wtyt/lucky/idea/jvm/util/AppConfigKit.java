package com.wtyt.lucky.idea.jvm.util;

import com.intellij.ide.util.PropertiesComponent;

/**
 * https://plugins.jetbrains.com/docs/intellij/persisting-state-of-components.html
 *
 * @author Lucky-maxinchun
 * @date 2021/7/14 14:42
 */
public class AppConfigKit
{
    private static final String KEY_JVM_PARAMETER = "com.wtyt.lucky.idea.jvm.util.AppConfigKit.jvmParameter";
    private static final String KEY_JVM_PARAMETER_LIST = "com.wtyt.lucky.idea.jvm.util.AppConfigKit.jvmParameterList";

    private static final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

    private AppConfigKit()
    {
    }

    public static String getJvmArguments()
    {
        return propertiesComponent.getValue(KEY_JVM_PARAMETER);
    }

    public static void setJvmParameter(String jvmParameter)
    {
        propertiesComponent.setValue(KEY_JVM_PARAMETER, jvmParameter);
    }

    public static String getJvmParameterList()
    {
        return propertiesComponent.getValue(KEY_JVM_PARAMETER_LIST);
    }

    public static void setJvmParameterList(String jvmParameterList)
    {
        propertiesComponent.setValue(KEY_JVM_PARAMETER_LIST, jvmParameterList);
    }
}
