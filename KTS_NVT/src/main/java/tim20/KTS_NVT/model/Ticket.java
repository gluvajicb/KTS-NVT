package tim20.KTS_NVT.model;

import java.sql.Date;
import java.util.List;

public abstract class Ticket {

	private Long id;
	private List<Date> date;
	private Boolean singleDay;
	private Double price;

	private Event event;
	private Sector sector;

	public Ticket() {
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

	public List<Date> getDate() {
		return date;
	}

	public void setDate(List<Date> date) {
		this.date = date;
	}

}