
package com.natwest.kata.api;

import com.natwest.kata.model.Direction;
import com.natwest.kata.service.IntersectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    private final IntersectionService service;

    public TrafficController(IntersectionService service) {
        this.service = service;
    }

    @PostMapping("/green/{direction}")
    public void green(@PathVariable Direction direction) {
        service.green(direction);
    }

    @PostMapping("/yellow/{direction}")
    public void yellow(@PathVariable Direction direction) {
        service.yellow(direction);
    }

    @PostMapping("/red/{direction}")
    public void red(@PathVariable Direction direction) {
        service.red(direction);
    }

    @PostMapping("/pause")
    public void pause() {
        service.pause();
    }

    @PostMapping("/resume")
    public void resume() {
        service.resume();
    }

    @GetMapping("/state")
    public Object state() {
        return service.currentState();
    }

    @GetMapping("/history")
    public Object history() {
        return service.history();
    }
}
