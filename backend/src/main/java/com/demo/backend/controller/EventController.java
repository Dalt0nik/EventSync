package com.demo.backend.controller;

import com.demo.backend.DTO.EventDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

    @GetMapping
    public EventDTO getEvents() {
        return null;
    }
}
