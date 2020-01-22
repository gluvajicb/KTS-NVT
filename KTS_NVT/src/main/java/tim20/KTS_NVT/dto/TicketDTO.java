package tim20.KTS_NVT.dto;

public class TicketDTO {
	
	private Long eventID;
    private Long eventDayID;
    private boolean isSingleDay;
    private double price;
    private Long sectorID;
    private int rowNumber;
    private int columnNumber;
    
    public TicketDTO() {
		this.columnNumber = -1;
		this.rowNumber = -1;
	}

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public Long getEventDayID() {
		return eventDayID;
	}

	public void setEventDayID(Long eventDayID) {
		this.eventDayID = eventDayID;
	}

	public boolean isSingleDay() {
		return isSingleDay;
	}

	public void setSingleDay(boolean isSingleDay) {
		this.isSingleDay = isSingleDay;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getSectorID() {
		return sectorID;
	}

	public void setSectorID(Long sectorID) {
		this.sectorID = sectorID;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public TicketDTO(Long eventID, Long eventDayID, boolean isSingleDay,
			double price, Long sectorID, int rowNumber, int columnNumber) {
		super();
		this.eventID = eventID;
		this.eventDayID = eventDayID;
		this.isSingleDay = isSingleDay;
		this.price = price;
		this.sectorID = sectorID;
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
	}
    
    
}
