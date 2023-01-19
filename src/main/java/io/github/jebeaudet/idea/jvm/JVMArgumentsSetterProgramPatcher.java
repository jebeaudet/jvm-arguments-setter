package io.github.jebeaudet.idea.jvm;

import org.apache.commons.lang3.StringUtils;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.JavaProgramPatcher;

import io.github.jebeaudet.idea.jvm.util.JVMArgumentsSetterPersistence;

import java.util.Set;

public class JVMArgumentsSetterProgramPatcher extends JavaProgramPatcher
{
    private static final Set<String> TEST_TYPES = Set.of("JUnit", "TestNG");
    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters)
    {
        if (JVMArgumentsSetterPersistence.getIsEnabled() && configuration instanceof RunConfiguration) {
            String runType = ((RunConfiguration) configuration).getType().getId();
            String jvmArguments = JVMArgumentsSetterPersistence.getJvmArguments(runType != null && TEST_TYPES.contains(runType));
            if (StringUtils.isNotBlank(jvmArguments)) {
                javaParameters.getVMParametersList().addParametersString(jvmArguments);
            }
        }
    }
}
