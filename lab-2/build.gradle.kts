plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    testCompileOnly("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")

    implementation("mysql:mysql-connector-java:8.0.32")

    implementation("org.hibernate:hibernate-core:6.1.7.Final")

    implementation("org.mybatis:mybatis:3.5.6")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}