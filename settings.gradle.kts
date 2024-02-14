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
    }
}

rootProject.name = "ShopApp"
include(":app")
include(":feature:registration")
include(":feature:main")
include(":feature:catalog")
include(":feature:product")
include(":feature:profile")
include(":feature:favorites")
include(":core:network")
include(":core:ui")
include(":core:common")
include(":core:local")
