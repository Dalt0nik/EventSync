import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createEvent, submitFeedback } from '../api/eventApi';
import { CreateEventDTO } from '../types/event';
import { CreateFeedbackDTO } from '../types/feedback';

export const useCreateEvent = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: (event: CreateEventDTO) => createEvent(event),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['events'] });
    },
  });
};

export const useSubmitFeedback = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: ({ eventId, feedback }: { eventId: string; feedback: CreateFeedbackDTO }) =>
      submitFeedback(eventId, feedback),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({ queryKey: ['event-summary', variables.eventId] });
      queryClient.invalidateQueries({ queryKey: ['feedbacks', variables.eventId] });
    },
  });
};