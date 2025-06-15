import { useState } from 'react';
import {
  Container,
  Typography,
  Card,
  CardContent,
  CardActions,
  Button,
  Grid,
  Box,
  CircularProgress,
  Alert,
  Fab,
} from '@mui/material';
import { Add } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { getAllEvents } from '../api/eventApi';
import { CreateEventDialog } from '../components/CreateEventDialog';

export const EventListPage = () => {
  const navigate = useNavigate();
  const [createDialogOpen, setCreateDialogOpen] = useState(false);

  const { data: events = [], isLoading, isError } = useQuery({
    queryKey: ['events'],
    queryFn: getAllEvents
  });

  const truncateText = (text: string, maxLength: number = 80) => {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
  };

  if (isLoading) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
          <CircularProgress />
        </Box>
      </Container>
    );
  }

  if (isError) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Alert severity="error">
          Failed to load events. Please try again later.
        </Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4, px: { xs: 2, sm: 3 } }}>
      <Typography 
        variant="h4" 
        component="h1" 
        gutterBottom
        sx={{ 
          fontSize: { xs: '1.75rem', sm: '2.125rem' },
          textAlign: { xs: 'center', sm: 'left' }
        }}
      >
        Events
      </Typography>
      
      {events && events.length === 0 ? (
        <Box sx={{ textAlign: 'center', py: 8 }}>
          <Typography variant="h6" color="text.secondary" gutterBottom>
            No events found
          </Typography>
          <Typography variant="body1" color="text.secondary">
            Create your first event to get started!
          </Typography>
        </Box>
      ) : (
        <Grid container spacing={{ xs: 2, sm: 3 }} sx={{ minWidth: { md: '800px', lg: '1000px' } }}>
          {events?.map((event) => (
            <Grid size={{ xs: 12, sm: 6, md: 4 }} key={event.id}>
              <Card sx={{ 
                height: '100%', 
                display: 'flex', 
                flexDirection: 'column',
                mx: { xs: 0, sm: 'auto' }
              }}>
                <CardContent sx={{ flexGrow: 1, p: { xs: 2, sm: 2 } }}>
                  <Typography 
                    variant="h6" 
                    component="h2" 
                    gutterBottom
                    sx={{ fontSize: { xs: '1.1rem', sm: '1.25rem' } }}
                  >
                    {truncateText(event.title, 60)}
                  </Typography>
                  <Typography 
                    variant="body2" 
                    color="text.secondary"
                    sx={{ fontSize: { xs: '0.85rem', sm: '0.875rem' } }}
                  >
                    {truncateText(event.description, 120)}
                  </Typography>
                </CardContent>
                <CardActions sx={{ p: { xs: 2, sm: 2 }, pt: 0 }}>
                  <Button
                    size="small"
                    variant="contained"
                    onClick={() => navigate(`/events/${event.id}`)}
                    sx={{ 
                      fontSize: { xs: '0.8rem', sm: '0.875rem' },
                      width: { xs: '100%', sm: 'auto' }
                    }}
                  >
                    View Details
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}

      <Fab
        color="primary"
        aria-label="add"
        sx={{ 
          position: 'fixed', 
          bottom: { xs: 24, sm: 16 }, 
          right: { xs: 24, sm: 16 },
          zIndex: 1000
        }}
        onClick={() => setCreateDialogOpen(true)}
      >
        <Add />
      </Fab>

      <CreateEventDialog
        open={createDialogOpen}
        onClose={() => setCreateDialogOpen(false)}
      />
    </Container>
  );
};