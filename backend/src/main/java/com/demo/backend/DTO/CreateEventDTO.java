package com.demo.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateEventDTO {

    @NotBlank(message = "Event title cannot be blank")
    @Size(max = 100, message = "Event title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Event description cannot be blank")
    @Size(max = 1000, message = "Event description cannot exceed 1000 characters")
    private String description;
}
