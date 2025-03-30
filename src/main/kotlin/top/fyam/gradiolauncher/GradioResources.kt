package top.fyam.gradiolauncher

import com.intellij.openapi.util.IconLoader

object GradioResources {
    val gradioIcon get() = IconLoader.getIcon("/icons/gradio.svg", javaClass)

    const val ACTION_NAME = "Run With Gradio"
    const val ACTION_DESC = "Run python file With Gradio"
}
