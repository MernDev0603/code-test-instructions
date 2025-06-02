# URL Shortener Coding Task

## Task

Build a simple **URL shortener** in a ** preferably JVM-based language** (e.g. Java, Kotlin).

It should:

- Accept a full URL and return a shortened URL.
- Persist the shortened URLs across restarts.
- Allow a user to **customise the shortened URL** (e.g. user provides `my-custom-alias` instead of a random string).
- Expose a **simple UI** (basic form is fine — no need for a polished design).
- Expose a **RESTful API** to perform create/read/delete operations on URLs.  
  → Refer to the provided [`openapi.yaml`](./openapi.yaml) for API structure and expected behaviour.
- Include the ability to **delete a shortened URL** via the API.
- **Have tests**.
- Be containerised (e.g. Docker).
- Include instructions for running locally.

## Rules

- Fork the repository and work in your fork. Do not push directly to the main repository.
- We suggest spending no longer than **4 hours**, but you can take longer if needed.
- Commit often with meaningful messages.
- Write tests.
- Use the provided [`openapi.yaml`](./openapi.yaml) as a reference.
- Focus on clean, maintainable code.

## Deliverables

- Working code.
- Simple UI.
- RESTful API matching the OpenAPI spec.
- Tests.
- Dockerfile.
- README with:
  - How to build and run locally.
  - Example usage (UI and/or API).
  - Any notes or assumptions.

## URL Shortener

A simple URL Shortener application built with:

- Backend: Java 17, Spring Boot, Maven, H2
- Frontend: React.js (Create React App)
- Dockerized with Docker Compose
- Fully compatible with provided OpenAPI specification

## Requirements

- Java 17
- Maven
- Node.js (>= 16.x)
- Docker (for full containerized run)

## How To Run

### Backend (Spring Boot)

```bash
cd backend
mvn clean package
java -jar target/urlshortener-0.0.1-SNAPSHOT.jar
```

Backend available at:
http://localhost:8080

### Frontend (React)

```bash
cd frontend
npm install
npm start
```

Frontend available at:
http://localhost:3000

### Full Dockerized Run

```bash
docker compose up --build
```

Frontend: http://localhost:3000
Backend: http://localhost:8080

## 📺 Demo

![Demo](demo.gif)