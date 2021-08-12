// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val gradleVersion by extra("7.0.0")
    val kotlinVersion by extra("1.5.20")
    val lifecycleVersion by extra("2.3.1")
    val koinVersion by extra("3.1.2")
    val roomVersion by extra("2.3.0")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.create("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}