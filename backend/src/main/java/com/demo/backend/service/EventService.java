package com.demo.backend.service;

import com.demo.backend.DTO.CreateEventDTO;
import com.demo.backend.DTO.EventDTO;
import com.demo.backend.Entity.Event;
import com.demo.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

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

    private EventDTO getEventDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .build();
    }
}
