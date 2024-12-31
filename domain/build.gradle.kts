plugins {
    kotlin("plugin.jpa")  //  entity 의 기본 생성자를 만들어주지 않아도 됨
}

version = "0.0.1"

allprojects {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.6")
        implementation("mysql:mysql-connector-java")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
    }
}
