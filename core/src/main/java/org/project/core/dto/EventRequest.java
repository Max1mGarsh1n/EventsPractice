package org.project.core.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {
    public String title;
    public String description;
    public LocalDateTime dateTime;
}
