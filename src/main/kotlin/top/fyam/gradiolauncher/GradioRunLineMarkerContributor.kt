package top.fyam.gradiolauncher

import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.jetbrains.python.PyTokenTypes
import com.jetbrains.python.PythonFileType
import com.jetbrains.python.codeInsight.dataflow.scope.ScopeUtil
import com.jetbrains.python.psi.PyFile
import com.jetbrains.python.psi.PyUtil
import com.jetbrains.python.psi.impl.getIfStatementByIfKeyword

class GradioRunLineMarkerContributor : RunLineMarkerContributor() {
    override fun getInfo(p0: PsiElement) = if (isMainClauseOnTopLevel(p0)) {
        val icon = AllIcons.RunConfigurations.TestState.Run
        val actions = arrayOf(GradioAction())
        Info(icon, actions, null)
    } else {
        null
    }
}

/**
 * Source: PyRunLineMarkerContributor
 */
private fun isMainClauseOnTopLevel(
    element: PsiElement,
) = if (element.node.elementType === PyTokenTypes.IF_KEYWORD) {
    val statement = getIfStatementByIfKeyword(element)
    val containingFile = ScopeUtil.getScopeOwner(element)
    statement != null &&
            containingFile is PyFile &&
            containingFile.virtualFile.fileType === PythonFileType.INSTANCE &&
            PyUtil.isIfNameEqualsMain(statement)
} else {
    false
}
