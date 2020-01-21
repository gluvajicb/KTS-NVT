package tim20.KTS_NVT.model;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class Sector {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private Double top;
	private Double posLeft;
	private Double width;
	private Double height;
	private Double angle;
	
	@ManyToOne
	private Location location;

	@OneToMany(mappedBy = "sector", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SectorPrice> sector_price;

	public Sector() {
		sector_price = new HashSet<SectorPrice>();
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

	@JsonIgnore
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@JsonIgnore
	public Set<SectorPrice> getSector_price() {
		return sector_price;
	}

	public void setSector_price(Set<SectorPrice> sector_price) {
		this.sector_price = sector_price;
	}

	public Double getTop() {
		return top;
	}

	public void setTop(Double top) {
		this.top = top;
	}

	public Double getPosLeft() {
		return posLeft;
	}

	public void setPosLeft(Double posLeft) {
		this.posLeft = posLeft;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getAngle() {
		return angle;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}
}