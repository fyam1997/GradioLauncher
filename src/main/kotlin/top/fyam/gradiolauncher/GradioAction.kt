package top.fyam.gradiolauncher

import com.intellij.execution.ProgramRunnerUtil
import com.intellij.execution.RunManager
import com.intellij.execution.RunManagerEx
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiFile
import com.intellij.sh.run.ShConfigurationType
import com.intellij.sh.run.ShRunConfiguration

class GradioAction : AnAction(
    GradioResources.ACTION_NAME,
    GradioResources.ACTION_DESC,
    GradioResources.gradioIcon,
) {
    override fun actionPerformed(event: AnActionEvent) {
        val file = event.getData(CommonDataKeys.PSI_FILE) ?: return

        val configSetting = createConfigSetting(file)

        val runManager = RunManagerEx.getInstanceEx(file.project)
        runManager.addConfiguration(configSetting)
        runManager.selectedConfiguration = configSetting
        ProgramRunnerUtil.executeConfiguration(configSetting, DefaultRunExecutor.getRunExecutorInstance())
    }

    private fun createConfigSetting(file: PsiFile): RunnerAndConfigurationSettings {
        val configurationSetting = RunManager.getInstance(file.project)
            .createConfiguration("gradio ${file.name}", ShConfigurationType.getInstance())

        val configuration = configurationSetting.configuration as ShRunConfiguration
        configuration.isExecuteInTerminal = true
        configuration.isExecuteScriptFile = false
        configuration.scriptText = "gradio ${file.virtualFile.path}"
        configuration.scriptWorkingDirectory = file.project.basePath
        return configurationSetting
    }
}
