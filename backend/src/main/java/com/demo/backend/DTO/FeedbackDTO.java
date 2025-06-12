package com.demo.backend.DTO;

import com.demo.backend.Entity.Sentiment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Data
public class FeedbackDTO {
    private UUID id;
    private String feedback;
    private Instant timestamp;
    private Sentiment sentiment;
}
