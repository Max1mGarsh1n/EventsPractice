package org.project.core.service;

import org.project.core.dto.EventRequest;
import org.project.core.entity.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final List<Event> events = new ArrayList<>();
    private Long lastId = 0L;

    public List<Event> getAll() {
        return new ArrayList<>(events);
    }

    public Event create(EventRequest req) {
        Event event = new Event(req.getTitle(), req.getDescription(), req.getDateTime());
        event.setId(++lastId);
        events.add(event);
        return event;
    }

    public Optional<Event> getById(Long id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public boolean delete(Long id) {
        return events.removeIf(e -> e.getId().equals(id));
    }
}
