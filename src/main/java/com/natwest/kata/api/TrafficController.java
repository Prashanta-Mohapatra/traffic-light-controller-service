
package com.natwest.kata.api;

import com.natwest.kata.model.Direction;
import com.natwest.kata.service.IntersectionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
