package com.demo.backend.exception;

import org.springframework.http.HttpStatus;

public class SentimentAnalysisException extends MainException {
  public SentimentAnalysisException(String message) {
    super(message, "Sentiment Analysis Failed", HttpStatus.SERVICE_UNAVAILABLE);
  }
}
