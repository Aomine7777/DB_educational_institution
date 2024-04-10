plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.mysql:mysql-connector-j:8.0.33")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    compileOnly("org.projectlombok:lombok:1.18.28")
    testImplementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.slf4j:slf4j-api:2.0.11")
}

tasks.test {
    useJUnitPlatform()
}