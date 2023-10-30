rootProject.name = "otusJava"
include("hw01-gradle")
include("hw04-generics")
include("hw06-annotations")
include("hw06-annotations:test-application")
include("hw06-annotations:test-framework")
include("hw08-gc")
include("hw10-bite-code")
include("hw12-solid")
include("hw15-patterns")
include("hw16-io")
include("hw18-jdbc")
include("hw21-jpql")

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