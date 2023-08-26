plugins {
    id("java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.flywaydb.flyway") version "8.2.0"


}

group = "com.kurepin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    compileOnly("org.flywaydb:flyway-mysql")
    compileOnly("org.flywaydb:flyway-core:9.16.0")
    compileOnly("org.projectlombok:lombok")
    implementation("mysql:mysql-connector-java:8.0.28")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    implementation("org.hibernate:hibernate-validator:6.1.0.Final")

}

flyway {
    url = "jdbc:mysql://localhost:3306/MySQL"
    user = "root"
    password = "123"
}

tasks.withType<Test> {
    useJUnitPlatform()
}