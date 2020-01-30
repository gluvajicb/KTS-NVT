package tim20.KTS_NVT.dto;

public class SectorPriceDTO
{
    private Long id;
    private Double price;
    private SectorDTO sector;
    private Long eventID;

    public SectorPriceDTO() {}

    public SectorPriceDTO(Long id, Double price, SectorDTO sectorID, Long eventID) {
        this.id = id;
        this.price = price;
        this.sector = sectorID;
        this.eventID = eventID;
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

    
    public SectorDTO getSector() {
		return sector;
	}

	public void setSector(SectorDTO sector) {
		this.sector = sector;
	}

	public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }
}
