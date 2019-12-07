dependencies {
  implementation(project(":pizza-core"))
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("io.jsonwebtoken:jjwt-api:0.10.6")
  runtime("io.jsonwebtoken:jjwt-impl:0.10.6")
  runtime("io.jsonwebtoken:jjwt-jackson:0.10.6")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
