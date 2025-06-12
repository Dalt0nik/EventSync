export interface EventDTO {
  id: string;
  title: string;
  description: string;
}

export interface EventSummaryDTO {
  id: string;
  title: string;
  description: string;
  totalFeedbacks: number;
  positiveFeedbacks: number;
  neutralFeedbacks: number;
  negativeFeedbacks: number;
  unevaluatedFeedbacks: number;
}

export interface CreateEventDTO {
  title: string;
  description: string;
}