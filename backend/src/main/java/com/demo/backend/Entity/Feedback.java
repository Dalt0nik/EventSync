package com.demo.backend.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 1000, nullable = false)
    private String feedback;

    @Column(length = 100)
    private String summary;

    @Column
    private Sentiment sentiment;

    @ManyToOne
    @JoinColumn(name = "event", nullable = false)
    private Event event;
}
