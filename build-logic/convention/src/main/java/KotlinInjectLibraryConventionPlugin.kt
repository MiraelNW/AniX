import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import utils.libs

class KotlinInjectLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }
            dependencies {
                "ksp"(libs.findLibrary("kotlin-inject-ksp").get())
                "implementation"(libs.findLibrary("kotlin-inject-runtime").get())
            }
        }
    }
}