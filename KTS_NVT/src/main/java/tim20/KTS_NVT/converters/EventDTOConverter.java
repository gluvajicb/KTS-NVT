package tim20.KTS_NVT.converters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tim20.KTS_NVT.dto.EventDTO;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventCategory;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.LocationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventDTOConverter {

    public static Event dtoToEvent(EventDTO dto)
    {
        EventService eventService = new EventService();

        Event event = new Event();

        event.setId(dto.getId());
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());

        /*
        event.setIsActive(dto.getActive());

        ENUM - EVENT CATEGORY

        if(dto.getEventCategory().equals(EventCategory.SHOW))
            event.setEventCategory(EventCategory.SHOW);
        else if (dto.getEventCategory().equals(EventCategory.SPORT))
            event.setEventCategory(EventCategory.SPORT);
        else if (dto.getEventCategory().equals(EventCategory.MUSIC))
            event.setEventCategory(EventCategory.MUSIC);

        event.setMax_tickets(dto.getMax_tickets());


        LOCATION
        event.setLocation(locationService.findOne(dto.getLocationID()));

        */

        return event;

    }

    public static EventDTO eventToDto(Event event) {

        EventDTO dto = new EventDTO();

        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());

        //dto = new EventDTO(event.getId(), event.getTitle(), event.getDescription());

        return dto;

    }

    public static List<EventDTO> eventsToDtos(Collection<Event> events) {

        List<EventDTO> retVal = new ArrayList<>();

        for (Event event : events) {
            retVal.add(EventDTOConverter.eventToDto(event));
        }

        return retVal;
    }
}
