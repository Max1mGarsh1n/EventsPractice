package org.example.events.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Event {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dateTime;

    public Event(String title, String description, LocalDateTime dateTime) {
        this.id = System.currentTimeMillis();
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }
}
