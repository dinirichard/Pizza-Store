plugins {
  java
  id("org.springframework.boot") version "2.1.2.RELEASE"
  id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

allprojects {
  apply(plugin = "java-library")
  apply(plugin = "org.springframework.boot")
  apply(plugin = "io.spring.dependency-management")

  configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
  }

  repositories {
    mavenCentral()
  }

  dependencies {
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    compileOnly("org.projectlombok:lombok:1.18.4")
    compileOnly("org.jasypt:jasypt:1.9.2")
    compileOnly("org.jasypt:jasypt-spring31:1.9.2")
    annotationProcessor("org.projectlombok:lombok:1.18.4")

    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-core:2.24.0")
    testImplementation("org.mockito:mockito-junit-jupiter:2.24.0")
    testImplementation("org.junit.platform:junit-platform-runner:1.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")
  }

  tasks.jar {
    enabled = true
  }

  tasks.test {
    useJUnitPlatform()
  }
}

dependencies {
  implementation(project(":pizza-main"))
  implementation(project(":pizza-core"))
  implementation(project(":infra-web"))
  implementation(project(":infra-jpa"))
  implementation(project(":infra-liquibase"))
}

tasks.wrapper {
  version = "5.2.1"
}

springBoot {
  mainClassName = "com.nortal.pizzastore.PizzastoreApplication"
}

val profiles: String? by project

tasks.bootRun {
  args = listOf(if (profiles != null) "--spring.profiles.active=$profiles" else "")
}
