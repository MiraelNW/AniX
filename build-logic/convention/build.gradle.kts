import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.miraeldev.vauma.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    // register the convention plugin
    plugins {
        register("androidLibrary") {
            id = "vauma.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "vauma.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("composeLibrary") {
            id = "vauma.compose.library"
            implementationClass = "ComposeLibraryConventionPlugin"
        }
        register("testLibrary") {
            id = "vauma.test.library"
            implementationClass = "TestLibraryConventionPlugin"
        }
        register("mviDecomposeLibrary") {
            id = "vauma.mvi.decompose.library"
            implementationClass = "MviDecomposeLibraryConventionPlugin"
        }
        register("CoilLibrary") {
            id = "vauma.coil.library"
            implementationClass = "CoilLibraryConventionPlugin"
        }
        register("KotlinInjectLibrary") {
            id = "vauma.kotlin.inject.library"
            implementationClass = "KotlinInjectLibraryConventionPlugin"
        }
        register("AppFlavorLibrary") {
            id = "vauma.app.flavor.library"
            implementationClass = "ApplicationFlavorsConventionPlugin"
        }
    }
}