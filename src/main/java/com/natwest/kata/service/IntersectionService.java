
package com.natwest.kata.service;

import com.natwest.kata.model.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class IntersectionService {

    private final Map<Direction, TrafficLight> lights = new EnumMap<>(Direction.class);
    private final List<String> history = new ArrayList<>();
    private boolean paused = false;

    public IntersectionService() {
        lights.put(Direction.NORTH_SOUTH, new TrafficLight());
        lights.put(Direction.EAST_WEST, new TrafficLight());
    }

    public synchronized void green(Direction direction) {
        ensureNotPaused();

        Direction opposite = opposite(direction);

        if (lights.get(opposite).getState() == LightState.GREEN) {
            throw new IllegalStateException("Conflicting directions cannot be GREEN");
        }

        lights.get(direction).toGreen();
        record(direction + " -> GREEN");
    }

    public synchronized void yellow(Direction direction) {
        lights.get(direction).toYellow();
        record(direction + " -> YELLOW");
    }

    private void ensureNotPaused() {
        if (paused) {
            throw new IllegalStateException("System is paused");
        }
    }

    private Direction opposite(Direction d) {
        return d == Direction.NORTH_SOUTH
                ? Direction.EAST_WEST
                : Direction.NORTH_SOUTH;
    }

    private void record(String event) {
        history.add(Instant.now() + " : " + event);
    }
}
