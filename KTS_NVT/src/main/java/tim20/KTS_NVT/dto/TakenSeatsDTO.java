package tim20.KTS_NVT.dto;

import java.util.ArrayList;
import java.util.List;

public class TakenSeatsDTO {

	private List<Stand> standTaken;
	private List<Seats> seatsTaken;

	public TakenSeatsDTO() {
		standTaken = new ArrayList<TakenSeatsDTO.Stand>();
		seatsTaken = new ArrayList<TakenSeatsDTO.Seats>();
	}

	public static class Stand {

		

		private long sectorId;
		private int count;
		
		public Stand() {
			// TODO Auto-generated constructor stub
		}
		
		public Stand(long sectorId, int count) {
			this.sectorId = sectorId;
			this.count = count;
		}
		
		public long getSectorId() {
			return sectorId;
		}

		public void setSectorId(long sectorId) {
			this.sectorId = sectorId;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
	}

	public static class Seats {
		private long sectorId;
		private List<Seat> seats;

		public Seats() {
			this.seats = new ArrayList<TakenSeatsDTO.Seat>();
		}

		public void addSeat(Seat seat) {
			this.seats.add(seat);
		}

		public long getSectorId() {
			return sectorId;
		}

		public void setSectorId(long sectorId) {
			this.sectorId = sectorId;
		}

		public List<Seat> getSeats() {
			return seats;
		}

		public void setSeats(List<Seat> seats) {
			this.seats = seats;
		}
	}

	public static class Seat {
		

		private int row;
		private int col;
		
		public Seat(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getCol() {
			return col;
		}

		public void setCol(int col) {
			this.col = col;
		}
	}

	public List<Stand> getStandTaken() {
		return standTaken;
	}

	public void setStandTaken(List<Stand> standTaken) {
		this.standTaken = standTaken;
	}

	public List<Seats> getSeatsTaken() {
		return seatsTaken;
	}

	public void setSeatsTaken(List<Seats> seatsTaken) {
		this.seatsTaken = seatsTaken;
	}
}
