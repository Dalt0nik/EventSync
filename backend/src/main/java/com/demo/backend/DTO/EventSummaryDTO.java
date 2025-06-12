package com.demo.backend.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class EventSummaryDTO {
    private UUID id;
    private String title;
    private String description;
    private int totalFeedbacks = 0;
    private int positiveFeedbacks = 0;
    private int neutralFeedbacks = 0;
    private int negativeFeedbacks = 0;
    private int unevaluatedFeedbacks = 0;
}
