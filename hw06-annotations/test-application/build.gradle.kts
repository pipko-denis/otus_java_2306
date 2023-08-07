import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("java")
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    implementation(project(":hw06-annotations:test-framework"))
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("testApplication")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.TestLaunchApp"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}