package tim20.KTS_NVT.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.repository.DayRepository;

@Service
public class EventDayService {
	
	@Autowired
	private DayRepository dayRepository;

	public EventDay findOne(Long id) {

        Optional<EventDay> ed = dayRepository.findById(id);

        if(ed.isPresent()) {
            return ed.get();
        }

        return null;
    }

    public List<EventDay> findAll() {

		List<EventDay> eventDays = dayRepository.findAll();

		return eventDays;
	}

	public EventDay saveEventDay(EventDay eventDay) {

		if (eventDay.getId() != null) {
			return null;
		}

		EventDay ed = dayRepository.save(eventDay);

		return ed;
	}

	public EventDay updateEventDay(EventDay eventDay) {

		if(eventDay.getId() != null) {
			return null;
		}

		EventDay ed = dayRepository.save(eventDay);

		return ed;
	}

	public void deleteEventDay(Long eventDayId) { dayRepository.deleteById(eventDayId);}
	
	public boolean checkAvailability(Date eventDate, Long locationId) {
		
		EventDay day = dayRepository.checkAvailability(eventDate, locationId);
		
		if(day == null) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
