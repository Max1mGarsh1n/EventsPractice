package org.project.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.core.dto.EventRequest;
import org.project.core.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    private EventService service;
    private final LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
    private final LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

    @BeforeEach
    void setUp() {
        service = new EventService();
    }

    @Test
    void create_ShouldRejectInvalidTitle() {
        EventRequest request = new EventRequest("", "Description", futureDate);

        assertThrows(IllegalArgumentException.class,
                () -> service.create(request));
    }

    @Test
    void create_ShouldRejectPastEvent() {
        EventRequest request = new EventRequest("Valid", "Desc", pastDate);

        assertThrows(IllegalArgumentException.class,
                () -> service.create(request));
    }

    @Test
    void getFutureEvents_ShouldReturnOnlyFutureEvents() {
        service.create(new EventRequest("Future", "Desc", futureDate));

        List<Event> result = service.getFutureEvents();

        assertEquals(1, result.size());
        assertEquals("Future", result.get(0).getTitle());
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
