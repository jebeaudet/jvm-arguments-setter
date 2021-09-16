package com.wtyt.lucky.idea.jvm;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.JavaProgramPatcher;
import com.wtyt.lucky.idea.jvm.util.AppConfigKit;

import org.apache.commons.lang3.StringUtils;

public class JVMArgumentAppenderProgramPatcher extends JavaProgramPatcher
{
    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters)
    {
        if (configuration instanceof RunConfiguration)
        {
            RunConfiguration runConfiguration = (RunConfiguration) configuration;

            String jvmArguments = AppConfigKit.getJvmArguments();
            if (StringUtils.isNotBlank(jvmArguments))
            {
                javaParameters.getVMParametersList().addParametersString(jvmArguments);
            }
        }
    }
}
