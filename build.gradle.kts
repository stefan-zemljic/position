plugins {
    kotlin("jvm") version "2.0.20"
    id("maven-publish")
    id("signing")
}

group = "ch.bytecraft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

java {
    withJavadocJar()
    withSourcesJar()
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "ch.bytecraft"
            artifactId = "position"
            version = "1.0.0"
        }

        withType<MavenPublication> {
            pom {
                packaging = "jar"
                name.set("Parser")
                description.set("A kotlin library for computing 'line:column' positions in strings.")
                url.set("https://github.com/stefan-zemljic/psoition")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("stefan-zemljic")
                        name.set("Stefan Zemljic")
                        email.set("stefan.zemljic@protonmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/stefan-zemljic/position.git")
                    developerConnection.set("scm:git:ssh://github.com:stefan-zemljic/position.git")
                    url.set("https://github.com/stefan-zemljic/position")
                }
            }
        }
    }

    repositories {
        mavenLocal()
    }
}