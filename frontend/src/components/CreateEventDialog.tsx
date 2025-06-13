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
import { useCreateEvent } from '../hooks/useEvents';
import { toast } from 'react-hot-toast';

interface CreateEventDialogProps {
  open: boolean;
  onClose: () => void;
}

export const CreateEventDialog: React.FC<CreateEventDialogProps> = ({ open, onClose }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [errors, setErrors] = useState<{ title?: string; description?: string }>({});
  
  const createEventMutation = useCreateEvent();

  const validateForm = () => {
    const newErrors: { title?: string; description?: string } = {};
    
    if (!title.trim()) {
      newErrors.title = 'Event title cannot be blank';
    } else if (title.length > 100) {
      newErrors.title = 'Event title cannot exceed 100 characters';
    }
    
    if (!description.trim()) {
      newErrors.description = 'Event description cannot be blank';
    } else if (description.length > 1000) {
      newErrors.description = 'Event description cannot exceed 1000 characters';
    }
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async () => {
    if (!validateForm()) return;

    try {
      await createEventMutation.mutateAsync({
        title: title.trim(),
        description: description.trim(),
      });
      
      toast.success('Event created successfully!');
      handleClose();
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Failed to create event');
    }
  };

  const handleClose = () => {
    setTitle('');
    setDescription('');
    setErrors({});
    onClose();
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle>Create New Event</DialogTitle>
      <DialogContent>
        <Box sx={{ pt: 1 }}>
          <TextField
            autoFocus
            label="Event Title"
            fullWidth
            variant="outlined"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            error={!!errors.title}
            helperText={errors.title || `${title.length}/100 characters`}
            sx={{ mb: 2 }}
          />
          
          <TextField
            label="Event Description"
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            error={!!errors.description}
            helperText={errors.description || `${description.length}/1000 characters`}
          />
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Cancel</Button>
        <Button
          onClick={handleSubmit}
          variant="contained"
          disabled={createEventMutation.isPending}
        >
          {createEventMutation.isPending ? 'Creating...' : 'Create Event'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};