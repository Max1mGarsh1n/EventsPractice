package org.example.events.service;

import org.example.events.dto.EventRequest;
import org.example.events.entity.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public boolean delete(String id) {
        return events.removeIf(e -> e.getId().equals(id));
    }
}
