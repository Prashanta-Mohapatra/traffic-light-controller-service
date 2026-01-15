# Traffic Light Controller Service Kata

## Overview
This project implements a simple **Traffic Light Controller API** for a single intersection.  
The purpose of this kata is to demonstrate **clean design**, **domain modelling**, and **test-driven reasoning** within a limited timebox.

The application exposes REST endpoints to control traffic light states, pause or resume the system, and retrieve the current state and change history.

---

## Assumptions
- Only **one intersection** is modelled
- Two directions are supported:
  - `NORTH_SOUTH`
  - `EAST_WEST`
- Traffic light state and history are maintained **in memory**
- No persistence or external systems are involved
- Light changes are **command-driven** (no automatic timers)
- The system is designed for **clarity and simplicity**

---

## Domain Model and Design
- Each direction is represented by a `TrafficLight`
- `TrafficLight` behaves as a **state machine**, enforcing valid transitions only:

RED → GREEN → YELLOW → RED


- Invalid transitions are **rejected at the domain level**
- State changes are explicit and controlled
- All state changes are recorded in an in-memory **history** for traceability

### Conflict Rules
- Enforced in `IntersectionService`
- **Opposing directions are never GREEN simultaneously**
- Business rules validated before state mutation

---

## API Endpoints

### Change Light State
| Method | URL | Description |
|--------|-----|-------------|
| POST | /traffic/green/{direction} | Turns the specified direction GREEN |
| POST | /traffic/yellow/{direction} | Turns the specified direction YELLOW |
| POST | /traffic/red/{direction} | Turns the specified direction RED |

### System Control
| Method | URL | Description |
|--------|-----|-------------|
| POST | /traffic/pause | Pauses the system |
| POST | /traffic/resume | Resumes the system |

### Query Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| GET | /traffic/state | Returns current state of all directions |
| GET | /traffic/history | Returns chronological list of all state changes |

---

## Example Flow

1. **Initial state**

GET /traffic/state
Response:
{
"NORTH_SOUTH": "RED",
"EAST_WEST": "RED"
}

2. **Turn NORTH_SOUTH GREEN**

POST /traffic/green/NORTH_SOUTH
Response: 200 OK

3. **Attempt EAST_WEST GREEN (conflict)**

POST /traffic/green/EAST_WEST
Response: 500 Internal Server Error
Body:
{
"timestamp": "2026-01-15T07:59:40.798+00:00",
"status": 500,
"error": "Internal Server Error",
"path": "/traffic/green/EAST_WEST"
}

4. **Transition NORTH_SOUTH to YELLOW**

POST /traffic/yellow/NORTH_SOUTH
Response: 200 OK

5. **Transition NORTH_SOUTH to RED**

POST /traffic/red/NORTH_SOUTH
Response: 200 OK

6. **Turn EAST_WEST GREEN (now allowed)**

POST /traffic/green/EAST_WEST
Response: 200 OK

7. **Pause the system**

POST /traffic/pause
Response: 200 OK

8. **Resume the system**

POST /traffic/resume
Response: 200 OK

9. **Check history**

GET /traffic/history
Response: [
"2026-01-15T14:00:00Z : NORTH_SOUTH -> GREEN",
"2026-01-15T14:01:00Z : NORTH_SOUTH -> YELLOW",
"2026-01-15T14:02:00Z : NORTH_SOUTH -> RED",
"2026-01-15T14:03:00Z : SYSTEM PAUSED",
"2026-01-15T14:04:00Z : SYSTEM RESUMED",
...
]


---

## Concurrency Considerations
- Service methods are **synchronized** to ensure thread safety
- Correctness prioritized over complexity
- Future refactoring can introduce finer-grained locking if needed

---

## Error Handling Strategy
- Controllers **do not catch domain exceptions**
- Exceptions propagate to Spring Boot's default JSON error response
- Keeps controllers clean and focused on request orchestration
- In production, a global `@ControllerAdvice` could standardize error responses

---

## Testing Approach
- Written using **JUnit 5**
- **Service-level tests** validate domain rules and invariants
- **Controller tests** validate request mapping and HTTP responses
- Test method names are descriptive and **read as documentation**

---

## Running the Application

### Prerequisites
- Java 21
- Maven 3.9+

### Start Application
```bash
mvn spring-boot:run
