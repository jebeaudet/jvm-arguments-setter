package com.wtyt.lucky.idea.jvm;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.JavaProgramPatcher;
import com.wtyt.lucky.idea.jvm.util.AppConfigKit;
import org.apache.commons.lang3.StringUtils;

/**
 * 运行前添加配置的自定义 jvm 参数
 *
 * @author Lucky-maxinchun
 * @date 2021/7/14 13:53
 */
public class MyJvmPreRunChecks extends JavaProgramPatcher {


    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters) {
        System.out.println("begin add jvm parameters...");
        if (configuration instanceof RunConfiguration) {
            RunConfiguration runConfiguration = (RunConfiguration) configuration;

            // 增加自定义jvm参数
            String jvmParameter = AppConfigKit.getJvmParameter();
            if (StringUtils.isNotBlank(jvmParameter)) {
                System.out.println(String.format("add jvm paramters: %s", jvmParameter));
                javaParameters.getVMParametersList().addParametersString(jvmParameter);
            }
        }
    }
}
