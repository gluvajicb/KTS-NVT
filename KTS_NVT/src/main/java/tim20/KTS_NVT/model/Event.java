package tim20.KTS_NVT.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
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

	private Boolean isActive;
	private EventCategory eventCategory;

	// Maksimalni broj karata koje mogu da se REZERVISU
	private Integer max_tickets;

	@ManyToOne
	private Location location;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SectorPrice> sectorPrice;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ticket> tickets;

	
	public Event(Long id, String title, String description, Set<EventDay> eventdays, Boolean isActive,
			EventCategory eventCategory, Integer max_tickets, Location location, Set<SectorPrice> sectorPrice,
			Set<Ticket> tickets) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.eventdays = eventdays;
		this.isActive = isActive;
		this.eventCategory = eventCategory;
		this.max_tickets = max_tickets;
		this.location = location;
		this.sectorPrice = sectorPrice;
		this.tickets = tickets;
	}

	public Event() {
		this.eventdays = new HashSet<>();
		this.tickets = new HashSet<>();
		this.sectorPrice = new HashSet<>();
	}

	public Integer getMax_tickets() { return max_tickets; }

	public void setMax_tickets(Integer max_tickets) { this.max_tickets = max_tickets; }

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