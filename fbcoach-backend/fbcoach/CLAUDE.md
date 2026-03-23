# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Web application for football coaches ("Web aplikacija za nogometne trenere"). Spring Boot 3.2.4 backend with JWT authentication, PostgreSQL, and a React frontend at `../../fbcoach-frontend/`.

## Build & Run Commands

```bash
# Run the application
mvn spring-boot:run

# Build (skip tests for faster iteration)
mvn clean install -DskipTests

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=ClassName

# Build Docker image (multi-stage, JDK 17)
docker build -t fbcoach .
```

**Default port:** 8080
**Java version:** 17

## Architecture

Standard 3-layer Spring Boot architecture:

```
hr.fer.fbcoach/
├── auth/          Authentication (register/login, JWT generation)
├── config/        Security config, JWT filter, beans (ModelMapper, PasswordEncoder)
├── model/         JPA entities + DTOs in model/dto/ + enums in model/util/
├── repository/    Spring Data JPA repositories
├── service/       Service interfaces + implementations in service/impl/ (*ServiceJpa.java)
└── rest/          REST controllers
```

**Key design decisions:**
- Services follow the interface + `*ServiceJpa` implementation pattern
- Entity ↔ DTO conversion uses `ModelMapper` (bean in `ApplicationConfig`)
- `@JsonManagedReference` / `@JsonBackReference` handle bidirectional JPA relationships to avoid JSON serialization loops
- `spring.main.allow-circular-references=true` is set intentionally

## Security

JWT-based stateless authentication:
- All routes are protected except `/auth/**`
- JWT extracted from `Authorization: Bearer <token>` header
- Token expiry: 24 minutes
- `Korisnik` (user/coach entity) implements `UserDetails`; loaded by email
- CORS allowed origin: `http://localhost:5173` (React dev server)

## Database

PostgreSQL via Hibernate with `ddl-auto=update` (schema auto-creates/updates — no migrations).

Environment variables override defaults:
- `DB_URL` (default: `jdbc:postgresql://localhost:5432/postgres`)
- `DB_USERNAME` (default: `postgres`)
- `DB_PASS`

## Domain Model

Core entities and their relationships:
- **Korisnik** — coach/user account; owns many `Team`s and `Report`s
- **Team** — football team; has many `Player`s (ManyToMany), `Training`s, `Match`es, `Tactics`
- **Player** — team player; linked to `PlayerMatchStats`, `HealthStatus`, `Attendance`
- **Match** — match record; linked to `TacticsApplication` and `PlayerMatchStats`

Enums live in `model/util/`: `Role`, `Position`, `TrainingType`, `AttendanceType`, `LocationType`, `PresentType`.

## API Endpoints

| Controller | Base Path |
|---|---|
| AuthenticationController | `/auth` |
| TeamController | `/team` |
| PlayerController | `/players` |
| MatchController | `/matches` |
| TrainingController | `/training` |
| HealthStatusController | `/healthstatus` |
| AttendanceController | `/attendance` |
| PlayerMatchStatsController | `/playermatchstats` |
| TacticsController | `/tactics` |
| TacticsApplicationController | `/tacticsapplication` |
| ReportController | `/report` |

All controllers use `@CrossOrigin("http://localhost:5173")`. Standard CRUD pattern: `GET /`, `GET /{id}`, `POST /add`, `PUT /edit/{id}`, `DELETE /delete/{id}`.
