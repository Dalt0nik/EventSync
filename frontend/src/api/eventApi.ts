import { api } from './api';
import type { EventDTO, CreateEventDTO, EventSummaryDTO } from '../types/event';
import type { FeedbackDTO, CreateFeedbackDTO } from '../types/feedback';

export async function getAllEvents(): Promise<EventDTO[]> {
  return (await api.get('/events')).data;
}

export async function createEvent(event: CreateEventDTO): Promise<EventDTO> {
  return (await api.post('/events', event)).data;
}

export async function getEventSummary(eventId: string): Promise<EventSummaryDTO> {
  return (await api.get(`/events/${eventId}/summary`)).data;
}

export async function getFeedbacks(eventId: string): Promise<FeedbackDTO[]> {
  return (await api.get(`/events/${eventId}/feedbacks`)).data;
}

export async function submitFeedback(eventId: string, feedback: CreateFeedbackDTO): Promise<FeedbackDTO> {
  return (await api.post(`/events/${eventId}/feedback`, feedback)).data;
}