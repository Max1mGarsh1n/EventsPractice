package org.example.events.service;

import org.example.events.dto.EventRequest;
import org.example.events.entity.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final List<Event> events = new ArrayList<>();

    public List<Event> getAll() {
        return events;
    }

    public Event create(EventRequest req) {
        Event event = new Event(req.title, req.description, req.dateTime);
        events.add(event);
        return event;
    }

    public Optional<Event> getById(String id) {
        return events.stream()
                .filter(e -> e.getId().toString().equals(id))
                .findFirst();
    }

    public boolean delete(String id) {
        return events.removeIf(e -> e.getId().equals(id));
    }
}
