package org.example.events.controller;

import org.example.events.dto.EventRequest;
import org.example.events.entity.Event;
import org.example.events.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService service) {
        this.eventService = service;
    }

    @GetMapping
    public List<Event> getAll() {
        return eventService.getAll();
    }

    @PostMapping
    public Event create(@RequestBody EventRequest req) {
        return eventService.create(req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable String id) {
        Optional<Event> event = eventService.getById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return eventService.delete(id) ? "Deleted" : "Not Found";
    }
}
