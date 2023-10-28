pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}

rootProject.name = "Vauma"
include ("app")
include(":features:signIn")
include(":features:signUp")
include(":core")
include(":core:presentation")
include(":core:extensions")
include(":core:theme")
include(":core:utils")
include(":features:forgotPassword")
include(":features:account")
include(":features:home")
include(":features:favourites")
include(":features:detailInfo")
include(":features:search")
include(":features:videoScreen")
include(":data")
include(":core:models:auth")
include(":core:models:anime")
include(":core:models:result")
include(":core:models:user")
