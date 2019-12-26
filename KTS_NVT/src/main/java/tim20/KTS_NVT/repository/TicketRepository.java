package tim20.KTS_NVT.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim20.KTS_NVT.model.Ticket;

public interface TicketRepository<T extends Ticket> extends JpaRepository<T, Long> {
				
	@Query("SELECT * FROM seats_ticket  WHERE event_id = ?1 AND row_num = ?2 AND col_num = ?3 AND sector_id = ?4 LIMIT 1")
	Ticket checkSeatsTicketAvailability(Long eventId, Integer rowNum, Integer colNum, Long sectorId);
	
	@Query("SELECT * FROM seats_ticket WHERE (event_id = ?1 AND row_num = ?2 AND col_num = ?3 AND sector_id = ?4 AND NOT single_day) OR (event_id = ?1 AND row_num = ?2 AND col_num = ?3 AND sector_id = ?4 AND single_day AND day_id = ?5) LIMIT 1")
	Ticket checkSeatsTicketSingleDayAvailability(Long eventId, Integer rowNum, Integer colNum, Long sectorId, Long dayId);
	
	@Query("SELECT COUNT(*) FROM stand_ticket WHERE (event_id = ?1 AND sector_id = ?2 AND NOT single_day) OR (event_id = ?1 AND sector_id = ?2 AND single_day AND day_id = ?3) ")
	Integer checkNumberOfGuests(Long eventId, Long sectorId, Long dayId); //LOSA LOGIKA
	
}
