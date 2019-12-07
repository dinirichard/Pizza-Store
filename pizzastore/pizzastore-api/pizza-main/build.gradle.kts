dependencies {
  implementation(project(":pizza-core"))
  implementation(project(":infra-web"))
  implementation(project(":infra-jpa"))
  implementation(project(":infra-liquibase"))

  implementation("org.springframework.boot:spring-boot-starter-web")
}
