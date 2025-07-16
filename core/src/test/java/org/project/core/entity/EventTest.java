package org.project.core.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime time = LocalDateTime.of(2025, 7, 16, 10, 0);

        Event e1 = new Event("Same", "Desc", time);
        e1.setId(1L);

        Event e2 = new Event("Same", "Desc", time);
        e2.setId(1L);

        Event e3 = new Event("Different", "Desc", time);
        e3.setId(2L);

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1, e3);
    }

    @Test
    void testToString() {
        Event event = new Event("Title", "Desc", LocalDateTime.of(2025, 7, 16, 14, 0));
        event.setId(999L);

        String str = event.toString();
        assertTrue(str.contains("Title"));
        assertTrue(str.contains("Desc"));
        assertTrue(str.contains("999"));
    }


    @Test
    void testEventConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Event event = new Event("Test title", "Test desc", now);

        assertEquals("Test title", event.getTitle());
        assertEquals("Test desc", event.getDescription());
        assertEquals(now, event.getDateTime());
    }

    @Test
    void testSetters() {
        Event event = new Event("a", "b", LocalDateTime.now());
        event.setId(123L);

        assertEquals(123L, event.getId());
    }
}
