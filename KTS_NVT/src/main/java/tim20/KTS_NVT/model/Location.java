package tim20.KTS_NVT.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String address;

	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Sector> sectors;

	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Event> events;

	public Location() {
		super();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}

	@JsonIgnore
	public Set<Event> getEvents() {return events; }

	public void setEvents(Set<Event> events) { this.events = events; }

}