package org.example.events.controller;

import org.example.events.dto.EventRequest;
import org.example.events.entity.Event;
import org.example.events.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return eventService.delete(id) ? "Deleted" : "Not Found";
    }
}
