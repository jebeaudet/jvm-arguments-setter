package io.github.jebeaudet.idea.jvm;

import org.apache.commons.lang3.StringUtils;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.JavaProgramPatcher;

import io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence;

public class JVMArgumentsSetterProgramPatcher extends JavaProgramPatcher
{
    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters)
    {
        if (configuration instanceof RunConfiguration) {
            String jvmArguments = JVMArgumentsSetterPersistence.getJvmArguments();
            if (StringUtils.isNotBlank(jvmArguments)) {
                javaParameters.getVMParametersList().addParametersString(jvmArguments);
            }
        }
    }
}
