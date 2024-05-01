import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

class TestLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            dependencies {
                add("implementation", libs.findLibrary("junit").get())
                add("implementation", libs.findLibrary("mockk").get())
                add("implementation", libs.findLibrary("kotlinx-coroutines-test").get())
                add("implementation", libs.findLibrary("turbine").get())
            }
        }
    }
}