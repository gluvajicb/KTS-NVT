package tim20.KTS_NVT.model;

import javax.persistence.Entity;

@Entity
public class StandSector extends Sector {

	private Integer maxGuests;

	public StandSector() {
	}

	public Integer getMaxGuests() {
		return maxGuests;
	}

	public void setMaxGuests(Integer maxGuests) {
		this.maxGuests = maxGuests;
	}

}