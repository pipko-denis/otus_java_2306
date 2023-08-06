rootProject.name = "otusJava"
include("hw01-gradle")
include("hw04-generics")
include("hw06-annotations")
include("hw06-annotations:test-application")
include("hw06-annotations:test-framework")
include("hw06-annotations:test-hello")

pluginManagement {
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val freefairlombok: String by settings


    plugins {
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("io.freefair.lombok") version freefairlombok
    }
}

//findProject(":hw06-annotations:test-application")?.name = "test-application"
//findProject(":hw06-annotations:test-framework")?.name = "test-framework"
//findProject(":hw06-annotations:test-hello")?.name = "test-hello"
