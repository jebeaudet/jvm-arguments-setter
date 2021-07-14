package com.wtyt.lucky.idea.jvm.util;

import com.intellij.ide.util.PropertiesComponent;

/**
 * 配置保存及获取
 * https://plugins.jetbrains.com/docs/intellij/persisting-state-of-components.html
 *
 * @author Lucky-maxinchun
 * @date 2021/7/14 14:42
 */
public class AppConfigKit {

    private static final String KEY_JMV_PARAMETER = "global-jvm-parameter.jvmParameter";
    private static final String KEY_JMV_PARAMETER_LIST = "global-jvm-parameter.jvmParameterList";

    /**
     * 简单数据的全局保存及获取
     */
    private static final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();;

    /**
     * 工具类，防止实例化
     */
    private AppConfigKit() {
    }

    /**
     * jvm 命令行参数，jvm 数据配置处理后数据
     * @return
     */
    public static String getJvmParameter() {
        return propertiesComponent.getValue(KEY_JMV_PARAMETER);
    }

    public static void setJvmParameter(String jvmParameter) {
        propertiesComponent.setValue(KEY_JMV_PARAMETER, jvmParameter);
    }

    /**
     * jvm 配置元信息
     * @return
     */
    public static String getJvmParameterList() {
        return propertiesComponent.getValue(KEY_JMV_PARAMETER_LIST);
    }

    public static void setJvmParameterList(String jvmParameterList) {
        propertiesComponent.setValue(KEY_JMV_PARAMETER_LIST, jvmParameterList);
    }


}
