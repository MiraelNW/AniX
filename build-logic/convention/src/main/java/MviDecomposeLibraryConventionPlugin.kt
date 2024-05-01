import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

class MviDecomposeLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            dependencies {
                add("implementation", libs.findLibrary("decompose").get())
                add("implementation", libs.findLibrary("decompose-jetpack-compose").get())
                add("implementation", libs.findLibrary("mvi-kotlin").get())
                add("implementation", libs.findLibrary("mvi-kotlin-main").get())
                add("implementation", libs.findLibrary("mvi-kotlin-coroutine-extensions").get())
                add("implementation", libs.findLibrary("mvi-kotlin-logging").get())
            }
        }
    }
}