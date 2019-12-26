package tim20.KTS_NVT.model;
import javax.persistence.Entity;

@Entity
public class StandTicket extends Ticket {

	public StandTicket() {}
	public StandTicket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector) {
		super(day, singleDay, price, event, sector);
	}
	
}