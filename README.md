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
  - Convention over Configuration 
  - Konfiguration über Umgebungsvariablen (umfangreich)
  - Server als Teil der Anwendung (Serverless)
  - Integrationstests
  - Starter Dependencies

## Anwendung starten

- main-Methode starten
- `http://localhost:9080/` (Startseite mit Links)
- Anmelden mit Username / Passwort
  - `user1` / `user1` (nur Leseberechtigungen mit Rolle `READER`)
  - `user2` / `user2` (Schreibberechtigungen mit Rolle `AUTHOR`)

oder:

```bash
mvn spring-boot:run
```

oder:

```bash
mvn clean package
java -jar target/blog-app-0.0.1-SNAPSHOT.jar
```

Mit `dev-Profil:

```bash
java -jar target/blog-app-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=dev
```

## Anwendung bauen

Gebaut wird wie üblich mit

```bash
mvn clean package
```

Es gibt jedoch die Möglichkeit, für AWS-Lambda zu bauen. Momentan wird dabei die Security
angepasst, sodass anonyme Zugriffe möglich sind.

```bash
mvn clean package -Paws-lambda
```

