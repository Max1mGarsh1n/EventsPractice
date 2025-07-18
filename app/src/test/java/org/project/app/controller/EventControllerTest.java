package org.project.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.project.core.dto.EventRequest;
import org.project.core.entity.Event;
import org.project.core.service.EventService;
import org.project.core.util.EventUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventControllerTest {

    private EventService eventService;
    private EventController controller;

    @BeforeEach
    void setUp() {
        eventService = mock(EventService.class);
        controller = new EventController(eventService);
    }

    @Test
    void getAll_shouldReturnEventsFromService() {
        List<Event> mockList = List.of(new Event("Title", "Desc", LocalDateTime.now()));
        when(eventService.getAll()).thenReturn(mockList);

        List<Event> result = controller.getAll();

        assertEquals(mockList, result);
        verify(eventService).getAll();
    }

    @Test
    void getById_shouldReturnEventIfFound() {
        Event event = new Event("t", "d", LocalDateTime.now());
        when(eventService.getById(1L)).thenReturn(Optional.of(event));

        ResponseEntity<Event> result = controller.getById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(event, result.getBody());
        verify(eventService).getById(1L);
    }

    @Test
    void getById_shouldReturn404IfNotFound() {
        when(eventService.getById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Event> result = controller.getById(99L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void create_shouldReturnCreatedEvent() {
        EventRequest req = new EventRequest();
        req.setTitle("test");
        req.setDescription("desc");
        req.setDateTime(LocalDateTime.now());

        Event created = new Event("test", "desc", req.getDateTime());
        when(eventService.create(req)).thenReturn(created);

        ResponseEntity<Event> result = controller.create(req);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(created, result.getBody());
        verify(eventService).create(req);
    }

    @Test
    void delete_shouldReturnOkIfDeleted() {
        when(eventService.delete(1L)).thenReturn(true);

        ResponseEntity<String> result = controller.delete(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Event deleted", result.getBody());
    }

    @Test
    void delete_shouldReturnNotFoundIfNotDeleted() {
        when(eventService.delete(1L)).thenReturn(false);

        ResponseEntity<String> result = controller.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Event not found", result.getBody());
    }

    @Test
    void getFutureEvents_shouldReturnList() {
        List<Event> events = List.of(new Event("F", "D", LocalDateTime.now().plusDays(1)));
        when(eventService.getFutureEvents()).thenReturn(events);

        List<Event> result = controller.getFutureEvents();

        assertEquals(events, result);
        verify(eventService).getFutureEvents();
    }

    @Test
    void validateEvent_shouldReturnOkWhenValid() {
        EventRequest req = new EventRequest();
        req.setTitle("Valid");
        req.setDescription("desc");
        req.setDateTime(LocalDateTime.now().plusDays(1));

        try (MockedStatic<EventUtils> mocked = mockStatic(EventUtils.class)) {
            mocked.when(() -> EventUtils.isValidTitle(any(Event.class))).thenReturn(true);
            mocked.when(() -> EventUtils.isFutureEvent(any(Event.class))).thenReturn(true);

            ResponseEntity<String> result = controller.validateEvent(req);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals("Event is valid", result.getBody());

            mocked.verify(() -> EventUtils.isValidTitle(any(Event.class)), times(1));
            mocked.verify(() -> EventUtils.isFutureEvent(any(Event.class)), times(1));
        }
    }


    @Test
    void validateEvent_shouldReturnBadRequestWhenInvalidTitle() {
        EventRequest req = new EventRequest();
        req.setTitle("");
        req.setDescription("desc");
        req.setDateTime(LocalDateTime.now().plusDays(1));

        try (MockedStatic<EventUtils> mocked = mockStatic(EventUtils.class)) {
            mocked.when(() -> EventUtils.isValidTitle(any(Event.class))).thenReturn(false);

            ResponseEntity<String> result = controller.validateEvent(req);

            assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
            assertEquals("Invalid event title", result.getBody());
        }
    }

    @Test
    void validateEvent_shouldReturnBadRequestWhenPastDate() {
        EventRequest req = new EventRequest();
        req.setTitle("T");
        req.setDescription("desc");
        req.setDateTime(LocalDateTime.now().minusDays(1));

        try (MockedStatic<EventUtils> mocked = mockStatic(EventUtils.class)) {
            mocked.when(() -> EventUtils.isValidTitle(any(Event.class))).thenReturn(true);
            mocked.when(() -> EventUtils.isFutureEvent(any(Event.class))).thenReturn(false);

            ResponseEntity<String> result = controller.validateEvent(req);

            assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
            assertEquals("Event date must be in the future", result.getBody());
        }
    }
}
