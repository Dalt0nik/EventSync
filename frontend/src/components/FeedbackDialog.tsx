import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Box,
} from '@mui/material';
import { useSubmitFeedback } from '../hooks/useEvents';
import { toast } from 'react-hot-toast';

interface FeedbackDialogProps {
  open: boolean;
  onClose: () => void;
  eventId: string;
}

export const FeedbackDialog: React.FC<FeedbackDialogProps> = ({ open, onClose, eventId }) => {
  const [feedback, setFeedback] = useState('');
  const [errors, setErrors] = useState<{ feedback?: string }>({});
  
  const submitFeedbackMutation = useSubmitFeedback();

  const validateForm = () => {
    const newErrors: { feedback?: string } = {};
    
    if (!feedback.trim()) {
      newErrors.feedback = 'Feedback cannot be blank';
    } else if (feedback.length > 1000) {
      newErrors.feedback = 'Feedback cannot exceed 1000 characters';
    }
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async () => {
    if (!validateForm()) return;

    try {
      await submitFeedbackMutation.mutateAsync({
        eventId,
        feedback: { feedback: feedback.trim() }
      });
      
      toast.success('Feedback submitted successfully!');
      handleClose();
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to submit feedback');
    }
  };

  const handleClose = () => {
    setFeedback('');
    setErrors({});
    onClose();
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle>Submit Feedback</DialogTitle>
      <DialogContent>
        <Box sx={{ pt: 1 }}>
          <TextField
            autoFocus
            label="Your Feedback"
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            value={feedback}
            onChange={(e) => setFeedback(e.target.value)}
            error={!!errors.feedback}
            helperText={errors.feedback || `${feedback.length}/1000 characters`}
            placeholder="Share your thoughts about this event..."
          />
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Cancel</Button>
        <Button
          onClick={handleSubmit}
          variant="contained"
          disabled={submitFeedbackMutation.isPending}
        >
          {submitFeedbackMutation.isPending ? 'Submitting...' : 'Submit Feedback'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};