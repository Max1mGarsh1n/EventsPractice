package org.project.core.util;

import org.project.core.entity.Event;

import java.time.LocalDateTime;

public class EventUtils {
    public static boolean isFutureEvent(Event event) {
        return event.getDateTime().isAfter(LocalDateTime.now());
    }

    public static boolean isValidTitle(Event event) {
        return event.getTitle() != null && !event.getTitle().isBlank();
    }
}
