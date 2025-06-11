package com.demo.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EventDTO {
    private UUID id;
    private String title;
    private String description;
}
