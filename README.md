# Sniper
Authored by [@felixtaiwo](https://github.com/felixtaiwo)

## Description
Sniper is a dynamic Spring Boot project that originated from an engaging coding challenge presented by Mintyn. It is just for the purpose of a challenge, hence it is kept as basic as possible

## Requirements
* Java 17+
* Maven (embedded in application)

## Getting Started with Development
Application is a single maven project with no internal sub modules. It uses an embedded in memory database (h2). This can be changed subsequently per request by just adding the necessary database driver dependency and adding the following parameters to the [property file](src/main/resources/application.properties)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=your-database-driver
spring.jpa.properties.hibernate.dialect=a-compatible-database-dialect
```

The user should **ENSURE** that secure properties should be changed in the [property file](src/main/resources/application.properties) just before using this application

#### Run locally
Start server using
```bash
./mvnw spring-boot:run
```

#### Run tests
```bash
./mvnw clean test
```

