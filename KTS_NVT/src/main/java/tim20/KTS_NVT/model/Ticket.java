package tim20.KTS_NVT.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
public abstract class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private EventDay day;
	private Boolean singleDay;
	private Double price;

	@ManyToOne
	private Event event;

	@ManyToOne
	private Sector sector;

	@ManyToOne
	private User user;

	public Ticket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector) {
		super();
		this.day = day;
		this.singleDay = singleDay;
		this.price = price;
		this.event = event;
		this.sector = sector;
	}

	public Ticket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector, User user) {
		super();
		this.day = day;
		this.singleDay = singleDay;
		this.price = price;
		this.event = event;
		this.sector = sector;
		this.user = user;
	}
}