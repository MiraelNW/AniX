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
include(":logger")
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
include(":data-database")
include(":imageloader")
include(":imageloader:api")
include(":imageloader:impl")
include(":core")
include(":core:designsystem")
include(":core:extensions")
include(":core:models")
include(":core:utils")
include(":core:theme")
include(":data")
include(":data:storeclient")
include(":data:storeclient:api")
include(":data:storeclient:impl")
include(":data:network")
include(":data:network:api")
include(":data:network:impl")
include(":data:repositories")
include(":data:repositories:api")
include(":data:repositories:impl")
