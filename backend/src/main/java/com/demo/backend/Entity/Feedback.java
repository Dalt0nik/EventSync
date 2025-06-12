package com.demo.backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 1000, nullable = false)
    private String feedback;

    @Column
    private Sentiment sentiment;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
