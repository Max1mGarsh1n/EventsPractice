package org.project.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.core.dto.EventRequest;
import org.project.core.entity.Event;
import org.project.core.service.EventService;
import org.project.core.util.EventUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<Event> getAll() {
        log.info("Fetching all events");
        return eventService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long id) {
        log.debug("Fetching event by ID: {}", id);
        return eventService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Event not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody EventRequest req) {
        log.info("Creating new event: {}", req.getTitle());
        Event createdEvent = eventService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @GetMapping("/future")
    public List<Event> getFutureEvents() {
        log.info("Fetching future events");
        return eventService.getFutureEvents();
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateEvent(@RequestBody EventRequest req) {
        log.info("Validating event: {}", req.getTitle());
        try {
            Event event = new Event(req.getTitle(), req.getDescription(), req.getDateTime());
            if (!EventUtils.isValidTitle(event)) {
                return ResponseEntity.badRequest().body("Invalid event title");
            }
            if (!EventUtils.isFutureEvent(event)) {
                return ResponseEntity.badRequest().body("Event date must be in the future");
            }
            return ResponseEntity.ok("Event is valid");
        } catch (Exception e) {
            log.error("Validation error", e);
            return ResponseEntity.internalServerError().body("Validation failed");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Deleting event with ID: {}", id);
        boolean deleted = eventService.delete(id);
        return deleted
                ? ResponseEntity.ok("Event deleted")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
    }
}