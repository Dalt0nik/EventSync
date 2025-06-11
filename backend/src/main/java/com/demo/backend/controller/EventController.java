package com.demo.backend.controller;

import com.demo.backend.DTO.CreateEventDTO;
import com.demo.backend.DTO.CreateFeedbackDTO;
import com.demo.backend.DTO.EventDTO;
import com.demo.backend.DTO.FeedbackDTO;
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
    public void createEvent(@RequestBody @Valid CreateEventDTO createEventDTO) {
        eventService.createEvent(createEventDTO);
    }

    @PostMapping("/{eventId}/feedback")
    public void submitFeedback(@PathVariable UUID eventId, @RequestBody @Valid CreateFeedbackDTO createFeedbackDTO) {
        eventService.createFeedback(eventId, createFeedbackDTO);
    }

    @GetMapping("/{eventId}/feedbacks")
    public List<FeedbackDTO> getFeedbacks(@PathVariable UUID eventId) {
        return eventService.getAllFeedbacks(eventId);
    }
}
