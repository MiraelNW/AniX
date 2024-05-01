import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import utils.libs

class ComposeLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            extensions.configure<LibraryExtension> {
                configureCompose(this)
            }
            dependencies {
                add("implementation", libs.findLibrary("activity-compose").get())
                add("implementation", libs.findLibrary("ui").get())
                add("implementation", libs.findLibrary("ui-tooling-preview").get())
                add("implementation", libs.findLibrary("ui-util").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("lifecycle-runtime-compose").get())

                add("implementation", libs.findLibrary("kotlinx-collections-immutable").get())

                add("implementation", libs.findLibrary("kaspresso").get())
                add("implementation", libs.findLibrary("kaspresso-compose").get())

                add("implementation", project(":core:models"))
                add("implementation", project(":core:utils"))
            }
        }
    }

    private fun Project.configureCompose(
        commonExtension: CommonExtension<*, *, *, *, *>,
    ) {
        commonExtension.apply {
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.0"
            }
        }
    }
}