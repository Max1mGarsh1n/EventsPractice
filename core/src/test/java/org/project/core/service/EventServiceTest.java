package org.project.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.core.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    private EventService service;

    @BeforeEach
    void setUp() {
        service = new EventService();
    }

    @Test
    void getFutureEvents_ShouldReturnEmptyListWhenNoFutureEvents() {
        List<Event> result = service.getFutureEvents();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAll_returnsEmptyInitially() {
        assertTrue(service.getAll().isEmpty());
    }

    @Test
    void delete_returnsFalseIfEventNotFound() {
        assertFalse(service.delete(999L));
    }

    @Test
    void getById_returnsEmptyIfNotFound() {
        assertTrue(service.getById(123L).isEmpty());
    }

    @Test
    void delete_removesEventSuccessfully() throws Exception {
        Event event = new Event("Test", "Desc", LocalDateTime.now());
        event.setId(1L);

        var eventsField = EventService.class.getDeclaredField("events");
        eventsField.setAccessible(true);
        List<Event> eventsList = (List<Event>) eventsField.get(service);
        eventsList.add(event);

        boolean deleted = service.delete(1L);
        assertTrue(deleted);
        assertTrue(service.getAll().isEmpty());
    }

}
