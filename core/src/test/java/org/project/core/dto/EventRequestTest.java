package org.project.core.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventRequestTest {

    @Test
    void testEventRequestGettersAndSetters() {
        LocalDateTime now = LocalDateTime.now();

        EventRequest request = new EventRequest();
        request.setTitle("Test Event");
        request.setDescription("Test Description");
        request.setDateTime(now);

        assertEquals("Test Event", request.getTitle());
        assertEquals("Test Description", request.getDescription());
        assertEquals(now, request.getDateTime());
    }

    @Test
    void testEventRequestEqualsAndHashCode() {
        LocalDateTime time = LocalDateTime.of(2025, 7, 16, 10, 0);

        EventRequest req1 = new EventRequest();
        req1.setTitle("Event");
        req1.setDescription("Desc");
        req1.setDateTime(time);

        EventRequest req2 = new EventRequest();
        req2.setTitle("Event");
        req2.setDescription("Desc");
        req2.setDateTime(time);

        assertEquals(req1, req2);
        assertEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    void testEventRequestToString() {
        EventRequest req = new EventRequest();
        req.setTitle("Sample");
        req.setDescription("Desc");
        req.setDateTime(LocalDateTime.of(2025, 7, 16, 12, 0));

        String str = req.toString();
        assertTrue(str.contains("Sample"));
        assertTrue(str.contains("Desc"));
        assertTrue(str.contains("2025"));
    }
}
