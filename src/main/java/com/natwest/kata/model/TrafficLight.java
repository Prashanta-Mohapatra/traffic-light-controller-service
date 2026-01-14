
package com.natwest.kata.model;

public class TrafficLight {

    private LightState state = LightState.RED;

    public LightState getState() {
        return state;
    }

    public void toGreen() {
        if (state != LightState.RED) {
            throw new IllegalStateException("Can only move to GREEN from RED");
        }
        state = LightState.GREEN;
    }

    public void toYellow() {
        if (state != LightState.GREEN) {
            throw new IllegalStateException("Can only move to YELLOW from GREEN");
        }
        state = LightState.YELLOW;
    }

    public void toRed() {
        if (state != LightState.YELLOW) {
            throw new IllegalStateException("Can only move to RED from YELLOW");
        }
        state = LightState.RED;
    }
}
