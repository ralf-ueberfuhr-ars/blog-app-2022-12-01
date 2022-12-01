# Blog App

## Ziele / Eigenheiten

### Spring Framework

- so wenig wie möglich Code schreiben (v.a. Boilerplate, stattdessen Annotationen)
- Dependency Injection
- Tests als 1st class citizen (TDD)
- Modularisierung (Single Responsibility)
- Integration von Standard-Technologien

### Spring Boot

- Cloud-Native
  - Konfiguration über Umgebungsvariablen (umfangreich)
  - Server als Teil der Anwendung (Serverless)
  - Integrationstests

## Anwendung starten

- main-Methode starten
- `http://localhost:9080/` (WhiteLabel Error Page)
- `http://localhost:9080/posts/findall`

oder:

```bash
mvn spring-boot:run
```

oder:

```bash
mvn clean package
java -jar target/blog-app-0.0.1-SNAPSHOT.jar
```
