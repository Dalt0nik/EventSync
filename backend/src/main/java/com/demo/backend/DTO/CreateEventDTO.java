package com.demo.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateEventDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;
}
