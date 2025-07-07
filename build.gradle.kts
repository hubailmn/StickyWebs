plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("xyz.jpenilla.run-paper") version "2.3.1"
}

version = "1.0-v1.20.4"
group = "cc.hubailmn"
description = "Sticky Webs for 1.20.4"

java {
  toolchain.languageVersion = JavaLanguageVersion.of(17)
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION

tasks.assemble {
  dependsOn(tasks.reobfJar)
}

repositories {
  mavenCentral()
  mavenLocal()
  maven { url = uri("https://jitpack.io") }
  maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
}


dependencies {
  paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
  compileOnly("org.projectlombok:lombok:1.18.32")
  annotationProcessor("org.projectlombok:lombok:1.18.32")

//  implementation("com.github.hubailmn:Utility:v1.1.5")
  implementation("cc.hubailmn:Utility:LocalBuild") {
    exclude(group = "org.reflections", module = "reflections")
  }
}

tasks {

  shadowJar {
    relocate("cc.hubailmn.utility", "cc.hubailmn.stickywebs.utility")

    minimize {
      exclude(dependency("com.github.hubailmn:Utility:.*"))
      exclude(dependency("cc.hubailmn:Utility:v1.0.0:.*"))
    }

    archiveClassifier.set("")
  }


  compileJava {
    options.release = 17
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }

  reobfJar {
    outputJar = layout.buildDirectory.file("/Users/hubailmn/Desktop/1/MC/Server-1.20.4/plugins/${project.name}-${project.version}.jar")
  }

}
