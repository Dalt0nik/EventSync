package com.demo.backend.DTO;

import com.demo.backend.Entity.Sentiment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class FeedbackDTO {
    private UUID id;
    private String feedback;
    private Sentiment sentiment;
}
