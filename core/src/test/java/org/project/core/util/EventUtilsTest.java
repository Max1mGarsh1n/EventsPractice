package org.project.core.util;

import org.junit.jupiter.api.Test;
import org.project.core.entity.Event;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventUtilsTest {

    @Test
    void testIsFutureEvent_true() {
        Event event = new Event("title", "desc", LocalDateTime.now().plusDays(1));
        assertTrue(EventUtils.isFutureEvent(event));
    }

    @Test
    void testIsFutureEvent_false() {
        Event event = new Event("title", "desc", LocalDateTime.now().minusDays(1));
        assertFalse(EventUtils.isFutureEvent(event));
    }

    @Test
    void testIsValidTitle_true() {
        Event event = new Event("Some title", "desc", LocalDateTime.now());
        assertTrue(EventUtils.isValidTitle(event));
    }

    @Test
    void testIsValidTitle_false_null() {
        Event event = new Event(null, "desc", LocalDateTime.now());
        assertFalse(EventUtils.isValidTitle(event));
    }

    @Test
    void testIsValidTitle_false_blank() {
        Event event = new Event("   ", "desc", LocalDateTime.now());
        assertFalse(EventUtils.isValidTitle(event));
    }
}
