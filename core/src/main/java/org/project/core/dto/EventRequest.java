package org.project.core.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class EventRequest {
    public String title;
    public String description;
    public LocalDateTime dateTime;

    public EventRequest(String s, String description, LocalDateTime futureDate) {
        this.title = s;
        this.description = description;
        this.dateTime = futureDate;
    }
}
