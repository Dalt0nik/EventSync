export interface FeedbackDTO {
  id: string;
  feedback: string;
  timestamp: string;
  sentiment: 'POSITIVE' | 'NEUTRAL' | 'NEGATIVE' | null;
}

export interface CreateFeedbackDTO {
  feedback: string;
}