package tim20.KTS_NVT.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim20.KTS_NVT.model.EventDay;

public interface DayRepository extends JpaRepository<EventDay, Long> {
	
	@Query("SELECT day FROM EventDay day WHERE day.eventdate = ?1 and day.event.location.id = ?2")
	EventDay checkAvailability(Date eventDate, Long locationId);
}
