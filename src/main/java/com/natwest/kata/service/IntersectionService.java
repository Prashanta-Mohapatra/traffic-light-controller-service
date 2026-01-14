
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

    public synchronized void red(Direction direction) {
        lights.get(direction).toRed();
        record(direction + " -> RED");
    }

    public synchronized void pause() {
        paused = true;
        record("SYSTEM PAUSED");
    }

    public synchronized void resume() {
        paused = false;
        record("SYSTEM RESUMED");
    }

    public Map<Direction, LightState> currentState() {
        Map<Direction, LightState> state = new EnumMap<>(Direction.class);
        lights.forEach((k, v) -> state.put(k, v.getState()));
        return state;
    }

    public List<String> history() {
        return history;
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
