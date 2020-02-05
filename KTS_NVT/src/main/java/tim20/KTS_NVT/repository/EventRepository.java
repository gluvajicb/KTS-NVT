package tim20.KTS_NVT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.Ticket;

public interface EventRepository extends JpaRepository<Event, Long>
{
	@Query("SELECT DISTINCT e FROM Event e JOIN e.eventdays ed WHERE ed.event.id = e.id AND ed.eventdate > current_date")
	List<Event> getUpcomingEvents();

}
