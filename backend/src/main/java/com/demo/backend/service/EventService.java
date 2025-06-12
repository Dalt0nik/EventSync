package com.demo.backend.service;

import com.demo.backend.DTO.*;
import com.demo.backend.Entity.Event;
import com.demo.backend.Entity.Feedback;
import com.demo.backend.Entity.Sentiment;
import com.demo.backend.exception.EntityNotFoundException;
import com.demo.backend.repository.EventRepository;
import com.demo.backend.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final FeedbackRepository feedbackRepository;

    private final SentimentAnalysisService sentimentAnalysisService;

    public EventDTO createEvent(CreateEventDTO createEventDTO) {
        Event event = Event.builder()
                .title(createEventDTO.getTitle())
                .description(createEventDTO.getDescription())
                .build();

        eventRepository.save(event);

        return getEventDTO(event);
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::getEventDTO)
                .toList();
    }

    public EventSummaryDTO getEventSummary(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found"));

        EventSummaryDTO eventSummaryDTO = new EventSummaryDTO();
        eventSummaryDTO.setId(event.getId());
        eventSummaryDTO.setTitle(event.getTitle());
        eventSummaryDTO.setDescription(event.getDescription());

        List<Feedback> feedbacks = feedbackRepository.findAllByEventId(eventId);

        int totalFeedbacks = feedbacks.size();
        int positiveFeedbacks = (int) feedbacks.stream()
                .filter(feedback -> feedback.getSentiment() == Sentiment.POSITIVE)
                .count();
        int neutralFeedbacks = (int) feedbacks.stream()
                .filter(feedback -> feedback.getSentiment() == Sentiment.NEUTRAL)
                .count();
        int negativeFeedbacks = (int) feedbacks.stream()
                .filter(feedback -> feedback.getSentiment() == Sentiment.NEGATIVE)
                .count();
        int unevaluatedFeedbacks = (int) feedbacks.stream()
                .filter(feedback -> feedback.getSentiment() == null)
                .count();

        eventSummaryDTO.setTotalFeedbacks(totalFeedbacks);
        eventSummaryDTO.setPositiveFeedbacks(positiveFeedbacks);
        eventSummaryDTO.setNeutralFeedbacks(neutralFeedbacks);
        eventSummaryDTO.setNegativeFeedbacks(negativeFeedbacks);
        eventSummaryDTO.setUnevaluatedFeedbacks(unevaluatedFeedbacks);

        return eventSummaryDTO;
    }

    public FeedbackDTO createFeedback(UUID eventId, CreateFeedbackDTO createFeedbackDTO) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + eventId + " not found"));

        Sentiment sentiment = sentimentAnalysisService.analyzeSentiment(createFeedbackDTO.getFeedback());

        Feedback feedback = Feedback.builder()
                .feedback(createFeedbackDTO.getFeedback())
                .timestamp(Instant.now())
                .sentiment(sentiment)
                .event(event)
                .build();

        feedbackRepository.save(feedback);

        return getFeedbackDTO(feedback);
    }

    public List<FeedbackDTO> getAllFeedbacks(UUID eventId) {
        eventRepository.findById(eventId)
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
        feedbackDTO.setTimestamp(feedback.getTimestamp());

        if (feedback.getSentiment() != null) {
            feedbackDTO.setSentiment(feedback.getSentiment());
        }
        return feedbackDTO;
    }
}
