package tim20.KTS_NVT.dto;

public class SectorPriceDTO
{
    private Long id;
    private Double price;
    private Long sectorID;
    private Long eventID;

    public SectorPriceDTO() {}

    public SectorPriceDTO(Long id, Double price, Long sectorID, Long eventID) {
        this.id = id;
        this.price = price;
        this.sectorID = sectorID;
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

    public Long getSectorID() {
        return sectorID;
    }

    public void setSectorID(Long sectorID) {
        this.sectorID = sectorID;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }
}
