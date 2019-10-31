package tim20.KTS_NVT.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public class Event {
	private Long id;
	private String title;
	private String description;
	private List<Date> dates;
	private Boolean isActive;
	private EventCategory eventCategory;

	private Location location;
	private Set<SectorPrice> sectorPrice;
	private Set<Ticket> tickets;

	public Event() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Set<SectorPrice> getSectorPrice() {
		return sectorPrice;
	}

	public void setSectorPrice(Set<SectorPrice> sectorPrice) {
		this.sectorPrice = sectorPrice;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public EventCategory getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

}