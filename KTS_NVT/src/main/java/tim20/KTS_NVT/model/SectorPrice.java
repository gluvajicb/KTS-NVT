package tim20.KTS_NVT.model;

import java.util.Set;

public class SectorPrice {
	
	private Long id;
	private Double price;

	private Set<Sector> sectors;

	public SectorPrice() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}

}