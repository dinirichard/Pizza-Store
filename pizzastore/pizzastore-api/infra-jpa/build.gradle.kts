dependencies {
  implementation(project(":pizza-core"))

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("com.h2database:h2")
  implementation("org.postgresql:postgresql")
}

tasks.jar {
  archiveFileName.set("pizza-jpa.jar")
}
