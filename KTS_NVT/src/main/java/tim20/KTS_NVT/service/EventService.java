package tim20.KTS_NVT.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim20.KTS_NVT.converters.EventDTOConverter;
import tim20.KTS_NVT.dto.EventDTO;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.repository.EventRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {

        List<Event> events = eventRepository.findAll();

        return events;
    }

    public Event findOne(Long id) {

        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            return event.get();
        }

        return null;
    }

    public Event saveEvent(Event event) {

        if(event.getId() != null) {
            return null;
        }

        Event e = eventRepository.save(event);

        return e;
    }


    public Event updateEvent(Event event) {

        if(event.getId() == null){
            return null;
        }

        Event e = eventRepository.save(event);

        return e;
    }

    public void deleteEvent(Long eventId) { eventRepository.deleteById(eventId);}

	public Collection<Event> getUpcomingEvents() {
		return eventRepository.getUpcomingEvents();
	}

}
