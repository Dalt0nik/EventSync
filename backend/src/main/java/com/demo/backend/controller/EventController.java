package com.demo.backend.controller;

import com.demo.backend.DTO.*;
import com.demo.backend.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventDTO> getEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public EventDTO createEvent(@RequestBody @Valid CreateEventDTO createEventDTO) {
        return eventService.createEvent(createEventDTO);
    }

    @PostMapping("/{eventId}/feedback")
    public FeedbackDTO submitFeedback(@PathVariable UUID eventId, @RequestBody @Valid CreateFeedbackDTO createFeedbackDTO) {
        return eventService.createFeedback(eventId, createFeedbackDTO);
    }

    @GetMapping("/{eventId}/feedbacks")
    public List<FeedbackDTO> getFeedbacks(@PathVariable UUID eventId) {
        return eventService.getAllFeedbacks(eventId);
    }

    @GetMapping("/{eventId}/summary")
    public EventSummaryDTO getEventSummary(@PathVariable UUID eventId) {
        return eventService.getEventSummary(eventId);
    }
}
