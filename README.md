# AVAILT - Service Booking Platform

A full-stack web application for booking services (catering, venues, events, and more).

## Tech stack

- **Frontend:** HTML, CSS, JavaScript (served by Spring Boot from `src/main/resources/static/`)
- **Backend:** Java 8+ / Spring Boot 2.7
- **Database:** H2 (default, in-memory) or MySQL (profile `mysql`)

## Prerequisites

- **Java 8 or newer** (project compiles with `--release 8` so it runs on JDK 8)
- **Maven 3.6+**

## Run the application

One command starts the API and the website:

```bash
cd /home/aaaa/AVAIL
mvn spring-boot:run
```

Open in the browser: **http://localhost:8080/**

Do **not** use `python -m http.server` for this project; the UI calls `/api/...` on the same host as the HTML.

### Port 8080 already in use

```bash
SERVER_PORT=8081 mvn spring-boot:run
```

Then open **http://localhost:8081/**

### MySQL instead of H2

1. Create database `availt` and apply `database/schema.sql` if needed.
2. Edit `src/main/resources/application-mysql.properties` (user/password).
3. Run:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## API endpoints

- `GET /api/categories` — category cards (name + image)
- `GET /api/services?category=&search=` — list services
- `GET /api/services/{id}` — one service
- `GET /api/menus?serviceId={id}` — menu / packages for a service
- `POST /api/bookings` — create booking (JSON body matches `Booking` entity)
- `GET /api/bookings`, `GET /api/bookings/{id}`

## Project layout

- `src/main/java/com/availt/` — Spring Boot code
- `src/main/resources/static/` — HTML, CSS, JS (index, categories, booking flow)
- `database/schema.sql` — MySQL schema and sample data

## Features

- Responsive marketplace-style UI
- Category grid, service listings, booking + menu selection + confirmation
- Sample data loaded on startup (`DataInitializer`) when using H2
