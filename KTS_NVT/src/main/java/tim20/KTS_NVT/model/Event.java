package tim20.KTS_NVT.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EventDay> eventdays;
	//private Set<Date> dates;
	private Boolean isActive;
	private EventCategory eventCategory;

	@ManyToOne
	private Location location;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SectorPrice> sectorPrice;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

	public Set<EventDay> getEventDays() {
		return eventdays;
	}

	public void setEventDays(Set<EventDay> eventdays) {
		this.eventdays = eventdays;
	}

	/*

	public Set<Date> getDates() {
		return dates;
	}

	public void setDates(Set<Date> dates) {
		this.dates = dates;
	}

	*/

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

	@JsonIgnore
	public Set<SectorPrice> getSectorPrice() {
		return sectorPrice;
	}

	public void setSectorPrice(Set<SectorPrice> sectorPrice) {
		this.sectorPrice = sectorPrice;
	}

	@JsonIgnore
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