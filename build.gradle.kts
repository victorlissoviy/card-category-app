plugins {
  java
  id("org.springframework.boot") version "3.3.3"
  id("io.spring.dependency-management") version "1.1.6"
}

group = "org.home.lissoviy"
version = "1"

var mapstructVersion = "1.6.0"
var collections4Version = "4.4"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(22)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.mapstruct:mapstruct:$mapstructVersion")
  implementation("org.apache.commons:commons-collections4:$collections4Version")

  compileOnly("org.projectlombok:lombok")

  runtimeOnly("org.postgresql:postgresql")

  annotationProcessor("org.projectlombok:lombok")
  annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

  testImplementation("org.mapstruct:mapstruct:$mapstructVersion")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")

  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  testAnnotationProcessor("org.projectlombok:lombok")
  testAnnotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.wrapper {
  version = "latest"
  distributionType = Wrapper.DistributionType.ALL
}

// Needs for show mapping information when start
tasks.named<JavaCompile>("compileJava") {
  options.compilerArgs.addAll(
    listOf(
      "-Amapstruct.suppressGeneratorTimestamp=true",
      "-Amapstruct.suppressGeneratorVersionInfoComment=true",
      "-Amapstruct.verbose=true"
    )
  )
}