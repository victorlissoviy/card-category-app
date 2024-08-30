plugins {
  id("java")
}

group = "org.home.lissoviy"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}

tasks.wrapper {
  version = "latest"
  distributionType = Wrapper.DistributionType.ALL
}