package org.example.events.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Event {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime dateTime;

    public Event(String title, String description, LocalDateTime dateTime) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }
}
