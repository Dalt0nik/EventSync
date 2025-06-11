package com.demo.backend.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class EventDTO {
    private UUID id;
    private String title;
    private String description;
}
