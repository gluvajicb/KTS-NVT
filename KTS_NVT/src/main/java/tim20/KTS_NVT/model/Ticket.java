package tim20.KTS_NVT.model;

import javax.persistence.*;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.sql.Date;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)

public abstract class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private EventDay day;
	private Boolean singleDay;
	private Double price;
	private Boolean paid;
	private Date reservationDate;

	@ManyToOne
	private Event event;

	@ManyToOne
	private Sector sector;

	@ManyToOne
	private User user;
	
	public Ticket() {}
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public EventDay getDay() {
		return day;
	}


	

	public Boolean getPaid() {
		return paid;
	}



	public void setPaid(Boolean paid) {
		this.paid = paid;
	}



	public Date getReservationDate() {
		return reservationDate;
	}



	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}



	public void setDay(EventDay day) {
		this.day = day;
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



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



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