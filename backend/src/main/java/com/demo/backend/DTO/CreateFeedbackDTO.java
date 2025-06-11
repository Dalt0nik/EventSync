package com.demo.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateFeedbackDTO {
    @NotBlank(message = "Feedback cannot be blank")
    @Size(max = 1000, message = "Feedback cannot exceed 1000 characters")
    private String feedback;
}
