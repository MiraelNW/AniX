import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import utils.configureFlavors
import utils.libs

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("vauma.kotlin.inject.library")
                apply("vauma.test.library")
                apply("vauma.mvi.decompose.library")
            }
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureFlavors(this)
                defaultConfig.targetSdk = 34
            }

            dependencies {
                add("implementation", libs.findLibrary("core-ktx").get())
                add("implementation", libs.findLibrary("kotlinx-collections-immutable").get())

                add("implementation", libs.findLibrary("activity-compose").get())
                add("implementation", libs.findLibrary("ui").get())
                add("implementation", libs.findLibrary("ui-tooling-preview").get())
                add("implementation", libs.findLibrary("ui-util").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("lifecycle-runtime-compose").get())

                add("implementation", libs.findLibrary("pluto").get())
                add("implementation", libs.findLibrary("pluto-network").get())
                add("implementation", libs.findLibrary("pluto-datastore").get())

                add("implementation", libs.findLibrary("accompanist-systemuicontroller").get())

                add("implementation", libs.findLibrary("core-splashscreen").get())

                add("implementation", libs.findLibrary("kaspresso").get())
                add("implementation", libs.findLibrary("kaspresso-compose").get())
            }
        }
    }

    private fun Project.configureKotlinAndroid(
        commonExtension: CommonExtension<*, *, *, *, *>,
    ) {
        commonExtension.apply {
            compileSdk = 34

            defaultConfig {
                minSdk = 24
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.0"
            }
            packaging {
                resources {
                    excludes += setOf("META-INF/LICENSE.md", "META-INF/LICENSE-notice.md", "/META-INF/{AL2.0,LGPL2.1}")
                }
            }
        }

        configureKotlin()
    }

    private fun Project.configureKotlin() {

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
                freeCompilerArgs = listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                        project.buildDir.absolutePath + "/compose_metrics"
                )
                freeCompilerArgs = listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                        project.buildDir.absolutePath + "/compose_metrics"
                )
            }
        }
    }
}