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
include ("vauma")
include(":data")
include(":logger")
include(":core-extensions")
include(":core-utils")
include(":core-theme")
include(":core-presentation")
include(":core-models")
include(":features-account")
include(":features-detailInfo")
include(":features-favourites")
include(":features-forgotPassword")
include(":features-home")
include(":features-search")
include(":features-signin")
include(":features-signup")
include(":features-video")
include(":network")
include(":network")
include(":data-network")
include(":data-store-client")
