import { useState } from 'react';
import {
  Container,
  Typography,
  Card,
  CardContent,
  Box,
  CircularProgress,
  Alert,
  Button,
  Grid,
} from '@mui/material';
import { 
  ArrowBack, 
  Feedback as FeedbackIcon,
  Assessment,
  SentimentSatisfied,
  SentimentNeutral,
  SentimentDissatisfied,
  Help
} from '@mui/icons-material';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { getEventSummary } from '../api/eventApi';
import { FeedbackDialog } from '../components/FeedbackDialog';

export const EventPage = () => {
  const { eventId } = useParams<{ eventId: string }>();
  const navigate = useNavigate();
  const [feedbackDialogOpen, setFeedbackDialogOpen] = useState(false);

  const { data: eventSummary, isLoading, isError } = useQuery({
    queryKey: ['event-summary', eventId],
    queryFn: () => getEventSummary(eventId!),
    enabled: !!eventId
  });

  if (isLoading) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  if (isError || !eventSummary) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Alert severity="error">
          Failed to load event. Please try again later.
        </Alert>
      </Container>
    );
  }

  const feedbackStats = [
    {
      label: 'Total Feedbacks',
      value: eventSummary.totalFeedbacks,
      color: 'primary' as const,
      icon: <Assessment sx={{ fontSize: 40 }} />
    },
    {
      label: 'Positive',
      value: eventSummary.positiveFeedbacks,
      color: 'success' as const,
      icon: <SentimentSatisfied sx={{ fontSize: 40 }} />
    },
    {
      label: 'Neutral',
      value: eventSummary.neutralFeedbacks,
      color: 'info' as const,
      icon: <SentimentNeutral sx={{ fontSize: 40 }} />
    },
    {
      label: 'Negative',
      value: eventSummary.negativeFeedbacks,
      color: 'error' as const,
      icon: <SentimentDissatisfied sx={{ fontSize: 40 }} />
    },
    {
      label: 'Unevaluated',
      value: eventSummary.unevaluatedFeedbacks,
      color: 'default' as const,
      icon: <Help sx={{ fontSize: 40 }} />
    }
  ];

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box sx={{ mb: 3 }}>
        <Button
          startIcon={<ArrowBack />}
          onClick={() => navigate('/')}
          sx={{ mb: 2 }}
        >
          Back to Events
        </Button>
        
        <Typography variant="h4" component="h1" gutterBottom>
          {eventSummary.title}
        </Typography>
        
        <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
          {eventSummary.description}
        </Typography>

        <Button
          variant="contained"
          startIcon={<FeedbackIcon />}
          onClick={() => setFeedbackDialogOpen(true)}
          sx={{ mb: 4 }}
        >
          Submit Feedback
        </Button>
      </Box>

      <Typography variant="h5" component="h2" gutterBottom>
        Feedback Summary
      </Typography>

      <Grid container spacing={3}>
        {feedbackStats.map((stat, index) => (
          <Grid size={{ xs: 12, sm: 6, md: 2.4 }} key={index}>
            <Card 
              sx={{ 
                height: '100%', 
                textAlign: 'center',
                '&:hover': {
                  boxShadow: 4,
                }
              }}
            >
              <CardContent sx={{ py: 3 }}>
                <Box 
                  sx={{ 
                    mb: 2, 
                    color: stat.color === 'default' ? 'text.secondary' : `${stat.color}.main`,
                    display: 'flex',
                    justifyContent: 'center'
                  }}
                >
                  {stat.icon}
                </Box>
                <Typography variant="h4" component="div" gutterBottom>
                  {stat.value}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {stat.label}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <FeedbackDialog
        open={feedbackDialogOpen}
        onClose={() => setFeedbackDialogOpen(false)}
        eventId={eventId!}
      />
    </Container>
  );
};