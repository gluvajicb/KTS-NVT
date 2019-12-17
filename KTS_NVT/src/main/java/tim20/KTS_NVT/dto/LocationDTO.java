package tim20.KTS_NVT.dto;

import java.util.ArrayList;
import java.util.List;

public class LocationDTO {

	private Long id;
	private String title;
	private String address;
	private List<SectorDTO> sectors = new ArrayList<SectorDTO>();
	//events
	public LocationDTO() {
	}

	public LocationDTO(Long id, String title, String address) {
		this.id = id;
		this.title = title;
		this.address = address;
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

	public List<SectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(List<SectorDTO> sectors) {
		this.sectors = sectors;
	}

}
