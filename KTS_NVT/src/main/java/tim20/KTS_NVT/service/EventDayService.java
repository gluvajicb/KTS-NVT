package tim20.KTS_NVT.service;

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
	
}
