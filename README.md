
# Traffic Light Controller Kata

## Assumptions
- Single intersection
- Two directions: NORTH_SOUTH and EAST_WEST
- In-memory state and history

## Design
- State machine enforced inside TrafficLight
- Conflict rules enforced centrally in IntersectionController
- Thread-safety via synchronized methods

## Run
mvn spring-boot:run

## Test
mvn test
