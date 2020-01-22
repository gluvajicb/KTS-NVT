package tim20.KTS_NVT.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim20.KTS_NVT.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
				
	@Query("SELECT st FROM seats_ticket st  WHERE st.event.id = ?1 AND st.rowNum = ?2 AND st.columnNum = ?3 AND st.sector.id = ?4")
	Ticket checkSeatsTicketAvailability(Long eventId, Integer rowNum, Integer colNum, Long sectorId);
	
	@Query("SELECT st FROM seats_ticket st WHERE (st.event.id = ?1 AND st.rowNum = ?2 AND st.columnNum = ?3 AND st.sector.id = ?4 AND st.singleDay=false) OR (st.event.id = ?1 AND st.rowNum = ?2 AND st.columnNum = ?3 AND st.sector.id = ?4 AND st.singleDay=true AND st.day.id = ?5)")
	Ticket checkSeatsTicketSingleDayAvailability(Long eventId, Integer rowNum, Integer colNum, Long sectorId, Long dayId);
	
	@Query("SELECT COUNT(st) FROM stand_ticket st WHERE (st.event.id = ?1 AND st.sector.id = ?2 AND singleDay=false) OR (st.event.id = ?1 AND st.sector.id = ?2 AND st.singleDay=true AND st.day.id = ?3) ")
	Integer checkNumberOfGuestsSingleDay(Long eventId, Long sectorId, Long dayId);
	
	@Query("SELECT COUNT(st) FROM stand_ticket st WHERE (st.event.id = ?1 AND st.sector.id = ?2 AND singleDay=false) OR (st.event.id = ?1 AND st.sector.id = ?2 AND st.singleDay=true AND st.day.id = ?3) ")
	Integer checkNumberOfGuests(Long eventId, Long sectorId, Long dayId); //LOSA LOGIKA
	
	
}
