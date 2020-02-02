package tim20.KTS_NVT.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name="seats_ticket")
@Getter
@Setter
@NoArgsConstructor
public class SeatsTicket extends Ticket {

	private Integer rowNum;
	private Integer columnNum;

	public SeatsTicket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector,
			Integer rowNum, Integer columnNum) {
		super( day, singleDay, price, event, sector);
		this.rowNum = rowNum;
		this.columnNum = columnNum;
	}

	public SeatsTicket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector,
					   Integer rowNum, Integer columnNum, User user) {
		super(day, singleDay, price, event, sector, user);
		this.rowNum = rowNum;
		this.columnNum = columnNum;
	}
}