package com.demo.backend.service;

import com.demo.backend.DTO.CreateEventDTO;
import com.demo.backend.DTO.CreateFeedbackDTO;
import com.demo.backend.DTO.EventDTO;
import com.demo.backend.DTO.FeedbackDTO;
import com.demo.backend.Entity.Event;
import com.demo.backend.Entity.Feedback;
import com.demo.backend.exception.EntityNotFoundException;
import com.demo.backend.repository.EventRepository;
import com.demo.backend.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final FeedbackRepository feedbackRepository;

    public void createEvent(CreateEventDTO createEventDTO) {
        Event event = Event.builder()
                .title(createEventDTO.getTitle())
                .description(createEventDTO.getDescription())
                .build();

        eventRepository.save(event);
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::getEventDTO)
                .toList();

    }

    public void createFeedback(UUID eventId, CreateFeedbackDTO createFeedbackDTO) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found"));

        Feedback feedback = Feedback.builder()
                .feedback(createFeedbackDTO.getFeedback())
                .event(event)
                .build();

        feedbackRepository.save(feedback);
    }

    public List<FeedbackDTO> getAllFeedbacks(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found"));

        List<Feedback> feedbacks = feedbackRepository.findAllByEventId(eventId);
        return feedbacks.stream()
                .map(this::getFeedbackDTO)
                .toList();
    }

    private EventDTO getEventDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .build();
    }

    private FeedbackDTO getFeedbackDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setFeedback(feedback.getFeedback());

        if (feedback.getSummary() != null) {
            feedbackDTO.setSummary(feedback.getSummary());
        }
        if (feedback.getSentiment() != null) {
            feedbackDTO.setSentiment(feedback.getSentiment());
        }
        return feedbackDTO;
    }
}
