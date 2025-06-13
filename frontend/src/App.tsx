import { Routes, Route } from 'react-router-dom';
import { EventListPage } from './pages/EventListPage';
import { EventPage } from './pages/EventPage';
import './App.css'

function App() {
  return (
    <Routes>
      <Route path="/" element={<EventListPage />} />
      <Route path="/events/:eventId" element={<EventPage />} />
    </Routes>
  )
}

export default App