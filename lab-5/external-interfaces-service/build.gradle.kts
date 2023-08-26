plugins {
    id("java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"

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

    compileOnly("org.projectlombok:lombok")
    implementation("mysql:mysql-connector-java:8.0.28")

    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.modelmapper:modelmapper:3.1.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    implementation("org.hibernate:hibernate-validator:6.1.0.Final")

    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.amqp:spring-rabbit:2.3.14")
    implementation("com.rabbitmq:amqp-client:5.12.0")

    implementation(project(":entities"))
    implementation(project(":employee-service"))
    implementation(project(":task-service"))
    implementation(project(":comment-service"))
    implementation(project(":RabbitMQ"))
    implementation(project(":database"))


}

tasks.withType<Test> {
    useJUnitPlatform()
}