package tim20.KTS_NVT.model;
import javax.persistence.Entity;

@Entity(name="stand_ticket")
public class StandTicket extends Ticket {

	public StandTicket() {}
	public StandTicket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector) {
		super(day, singleDay, price, event, sector);
	}
	public StandTicket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector, User user) {
		super(day, singleDay, price, event, sector, user);
	}
}