package tim20.KTS_NVT.converters;

import tim20.KTS_NVT.controller.EventController;
import tim20.KTS_NVT.dto.EventDayDTO;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.service.EventService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventDayDTOConverter {


    public static EventDay dtoToEventDay(EventDayDTO dto) {

        EventDay eventDay = new EventDay();

        eventDay.setId(dto.getId());
        eventDay.setTitle(dto.getTitle());
        eventDay.setEventDate(dto.getEventdate());

        EventService es = new EventService();

        Event event = es.findOne(dto.getId());

        eventDay.setEvent(event);

        return eventDay;
    }

    public static EventDayDTO eventDayToDto(EventDay eventDay) {

        EventDayDTO dto = new EventDayDTO(eventDay.getId(), eventDay.getTitle(), eventDay.getEventDate(), eventDay.getEvent().getId());

        return dto;
    }

    public static List<EventDayDTO> eventDaysToDtos(Collection<EventDay> eventdays) {

        List<EventDayDTO> retVal = new ArrayList<>();

        for (EventDay eventDay : eventdays) {
            retVal.add(EventDayDTOConverter.eventDayToDto(eventDay));
        }

        return retVal;
    }

}
