package tim20.KTS_NVT.dto;

import java.sql.Date;

public class TicketDTO {
	
	private Long ID;
    private String eventDayTitle;
    private boolean isSingleDay;
    private double price;
    private String sectorTitle;
    private int rowNumber;
    private int columnNumber;
    
    private Boolean paid;
	private String reservationDate;
	
	
    
    public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public String getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

	public TicketDTO() {
		this.columnNumber = -1;
		this.rowNumber = -1;
	}

	public Long getID() {
		return ID;
	}

	public void setEventID(Long eventID) {
		this.ID = eventID;
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
	

	public String getEventDayTitle() {
		return eventDayTitle;
	}

	public void setEventDayTitle(String eventDayTitle) {
		this.eventDayTitle = eventDayTitle;
	}

	public String getSectorTitle() {
		return sectorTitle;
	}

	public void setSectorTitle(String sectorTitle) {
		this.sectorTitle = sectorTitle;
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

	public TicketDTO(Long eventID, String eventDayID, boolean isSingleDay,
			double price, String sectorID, int rowNumber, int columnNumber, boolean paid, String date) {
		super();
		this.ID = eventID;
		this.eventDayTitle = eventDayID;
		this.isSingleDay = isSingleDay;
		this.price = price;
		this.sectorTitle = sectorID;
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.paid = paid;
		this.reservationDate = date;
	}
    
    
}
