package tim20.KTS_NVT.model;

import javax.persistence.*;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private EventDay day;
	//private List<Date> date;
	private Boolean singleDay;
	private Double price;

	@ManyToOne
	private Event event;

	@ManyToOne
	private Sector sector;

	public Ticket() {
	}

	
	public Ticket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector) {
		super();
		this.day = day;
		this.singleDay = singleDay;
		this.price = price;
		this.event = event;
		this.sector = sector;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getSingleDay() {
		return singleDay;
	}

	public void setSingleDay(Boolean singleDay) {
		this.singleDay = singleDay;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public EventDay getDay() {
		return day;
	}

	public void setDay(EventDay day) {
		this.day = day;
	}

	/*

	public List<Date> getDate() {
		return date;
	}

	public void setDate(List<Date> date) {
		this.date = date;
	}

	*/

}