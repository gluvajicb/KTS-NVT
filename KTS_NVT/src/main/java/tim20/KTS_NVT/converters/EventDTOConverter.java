package tim20.KTS_NVT.converters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tim20.KTS_NVT.controller.EventDayController;
import tim20.KTS_NVT.dto.EventDTO;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.EventCategory;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.LocationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventDTOConverter {
	static LocationService ls = new LocationService();

    public static Event dtoToEvent(EventDTO dto)
    {
        Event event = new Event();

        event.setId(dto.getId());
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        
        event.setIsActive(dto.getActive());

        if(dto.getEventCategory().equals("SHOW"))
            event.setEventCategory(EventCategory.SHOW);
        else if (dto.getEventCategory().equals("SPORT"))
            event.setEventCategory(EventCategory.SPORT);
        else if (dto.getEventCategory().equals("MUSIC"))
            event.setEventCategory(EventCategory.MUSIC);


        Location location = ls.findOne(dto.getLocationID());
        event.setLocation(location);

        event.setEventDays(EventDayDTOConverter.convertDTOsToEventDays(dto.getDays(),event));
        return event;

    }

    public static EventDTO eventToDto(Event event) {

        EventDTO dto = new EventDTO();

        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());

        if(event.getEventCategory().equals(EventCategory.SHOW))
            dto.setEventCategory("SHOW");
        else if (event.getEventCategory().equals(EventCategory.SPORT))
        	dto.setEventCategory("SPORT");
        else if (event.getEventCategory().equals(EventCategory.MUSIC))
        	dto.setEventCategory("MUSIC");
        
        dto.setActive(event.getIsActive());
        dto.setLocationID(event.getLocation().getId());
        
        dto.setDays(EventDayDTOConverter.eventDaysToDtos(event.getEventDays()));
        
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
