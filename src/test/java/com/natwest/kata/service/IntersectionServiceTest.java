package com.natwest.kata.service;

import com.natwest.kata.model.Direction;
import com.natwest.kata.model.LightState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionServiceTest {

    private IntersectionService service;

    @BeforeEach
    void setUp() {
        service = new IntersectionService();
    }

    @Test
    @DisplayName("A direction can be set to GREEN when the opposite direction is not GREEN")
    void allowGreenWhenNoConflict() {
        service.green(Direction.NORTH_SOUTH);

        assertEquals(LightState.GREEN,
                service.currentState().get(Direction.NORTH_SOUTH));
    }

    @Test
    @DisplayName("Conflicting directions cannot be GREEN at the same time")
    void preventConflictingGreenDirections() {
        service.green(Direction.NORTH_SOUTH);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.green(Direction.EAST_WEST)
        );

        assertEquals("Conflicting directions cannot be GREEN", ex.getMessage());
    }

    @Test
    @DisplayName("A direction can transition to YELLOW only from GREEN")
    void changeToYellow() {
        service.green(Direction.NORTH_SOUTH);
        service.yellow(Direction.NORTH_SOUTH);

        assertEquals(
                LightState.YELLOW,
                service.currentState().get(Direction.NORTH_SOUTH)
        );
    }

    @Test
    @DisplayName("A direction can transition to RED only from YELLOW")
    void changeToRed() {
        service.green(Direction.NORTH_SOUTH);
        service.yellow(Direction.NORTH_SOUTH);
        service.red(Direction.NORTH_SOUTH);

        assertEquals(
                LightState.RED,
                service.currentState().get(Direction.NORTH_SOUTH)
        );
    }

    @Test
    @DisplayName("System can be paused and resumed")
    void pauseAndResumeSystem() {
        service.pause();

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.green(Direction.NORTH_SOUTH)
        );

        assertEquals("System is paused", ex.getMessage());

        service.resume();
        service.green(Direction.NORTH_SOUTH);

        assertEquals(LightState.GREEN,
                service.currentState().get(Direction.NORTH_SOUTH));
    }

    @Test
    @DisplayName("Every state change is recorded in history")
    void recordHistoryForStateChanges() {
        service.green(Direction.NORTH_SOUTH);
        service.yellow(Direction.NORTH_SOUTH);
        service.red(Direction.NORTH_SOUTH);

        assertEquals(3, service.history().size());
    }
}
