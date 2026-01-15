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

