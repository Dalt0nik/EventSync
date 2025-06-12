package com.demo.backend.service;

import com.demo.backend.Entity.Sentiment;
import com.demo.backend.exception.SentimentAnalysisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class SentimentAnalysisService {

    private final String apiKey;

    private final WebClient webClient;

    private final String model;

    private final int timeout;

    public SentimentAnalysisService(
            @Value("${HUGGINGFACE_API_KEY}") String apiKey,
            @Value("${huggingface.api.base-url}") String baseUrl,
            @Value("${huggingface.api.model}") String model,
            @Value("${huggingface.api.timeout}") int timeout) {

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("HUGGINGFACE_API_KEY environment variable is not set");
        }

        if (!apiKey.startsWith("hf_")) {
            throw new IllegalStateException("Invalid Hugging Face API key format. Should start with 'hf_'");
        }

        this.apiKey = apiKey;
        this.model = model;
        this.timeout = timeout;

        // Configure WebClient
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();

        log.info("SentimentAnalysisService initialized with model: {} (timeout: {}ms)", model, timeout);
    }

    public Sentiment analyzeSentiment(String text) {
        log.debug("Analyzing sentiment for text: {}", text.substring(0, Math.min(50, text.length())));

        try {
            Map<String, String> requestBody = Map.of("inputs", text);

            List<List<Map<String, Object>>> response = webClient
                    .post()
                    .uri("/" + model)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(List.class)
                    .timeout(Duration.ofMillis(timeout))
                    .block();

            return parseResponse(response);

        } catch (WebClientException ex) {
            log.error("Failed to call Hugging Face API: {}", ex.getMessage());
            throw new SentimentAnalysisException("Sentiment analysis API unavailable: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error during sentiment analysis", ex);
            throw new SentimentAnalysisException("Failed to analyze sentiment: " + ex.getMessage());
        }
    }


    private Sentiment parseResponse(List<List<Map<String, Object>>> response) {
        if (response == null || response.isEmpty() || response.get(0).isEmpty()) {
            throw new SentimentAnalysisException("Empty response from sentiment analysis API");
        }

        List<Map<String, Object>> sentiments = response.get(0);

        // Find the sentiment with highest confidence
        Map<String, Object> bestResult = sentiments.stream()
                .max(Comparator.comparingDouble(a -> ((Number) a.get("score")).doubleValue()))
                .orElseThrow(() -> new SentimentAnalysisException("No sentiment results found"));

        String label = (String) bestResult.get("label");
        Double confidence = ((Number) bestResult.get("score")).doubleValue();

        Sentiment sentiment = mapLabelToSentiment(label);

        log.debug("Sentiment analysis result: {} (confidence: {})", sentiment, confidence);

        return sentiment;
    }

    private Sentiment mapLabelToSentiment(String label) {
        // Hugging Face model returns: LABEL_0 (negative), LABEL_1 (neutral), LABEL_2 (positive)
        return switch (label.toUpperCase()) {
            case "LABEL_0" -> Sentiment.NEGATIVE;
            case "LABEL_1" -> Sentiment.NEUTRAL;
            case "LABEL_2" -> Sentiment.POSITIVE;
            default -> {
                log.warn("Unknown sentiment label: {}, defaulting to NEUTRAL", label);
                yield Sentiment.NEUTRAL;
            }
        };
    }
}
