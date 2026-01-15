# Traffic Light Controller Service Kata

## Overview
This project implements a simple **Traffic Light Controller API** for an intersection.  
The purpose of this kata is not to build a fully production-ready traffic system, but to demonstrate **clear design thinking**, **domain modelling**, and **test-driven reasoning** within a limited timebox.

The application exposes REST endpoints to control traffic light states, pause or resume the system, and retrieve the current state and change history.

---

## Assumptions
To keep the solution focused and readable, the following assumptions were made:

- A **single intersection** is modelled
- Two directions are supported:
    - `NORTH_SOUTH`
    - `EAST_WEST`
- Traffic light state and history are maintained **in memory**
- No persistence or external systems are involved
- Light changes are **command-driven** (no automatic timers)

These assumptions were chosen intentionally to prioritise **simplicity and clarity**.

---

## Domain Model and Design
- Each direction is represented by a `TrafficLight`
- `TrafficLight` behaves as a **state machine**, enforcing valid transitions only:

RED → GREEN → YELLOW → RED


- Invalid transitions are rejected at the domain level
- State changes are explicit and controlled

### Conflict Rules
- Conflict rules are enforced centrally in `IntersectionService`
- Opposing directions are never GREEN at the same time
- Business rules are validated before state mutation

### History Tracking
- Every state change is recorded in an in-memory history
- History enables traceability and debugging

---

## API Endpoints

### Change Light State
- `POST /traffic/green/{direction}`
- `POST /traffic/yellow/{direction}`
- `POST /traffic/red/{direction}`

### System Control
- `POST /traffic/pause`
- `POST /traffic/resume`

### Query
- `GET /traffic/state`
- `GET /traffic/history`

---

## Concurrency Considerations
- Service methods are `synchronized`
- Ensures thread safety and correctness
- Designed for simplicity with scope for future optimisation

---

## Error Handling Strategy
- Controllers do not catch domain exceptions
- Exceptions propagate to Spring’s default error handling
- Controllers remain focused on orchestration
- A global `@ControllerAdvice` can be added in production

---

## Testing Approach
- Tests are written using **JUnit 5**
- Service tests validate domain rules and invariants
- Controller tests validate request mapping and responses
- Test names are written to read as documentation

---

## Running the Application

### Prerequisites
- Java 21
- Maven 3.9+

### Start Application
```bash
mvn spring-boot:run
