package tim20.KTS_NVT.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.dto.TicketDTO;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.SeatsTicket;
import tim20.KTS_NVT.model.StandTicket;
import tim20.KTS_NVT.model.Ticket;

public class TicketDTOConverter {
	
	public static TicketDTO seatsTicketToDto(SeatsTicket ss) {

		TicketDTO dto = new TicketDTO(ss.getId(), ss.getDay().getTitle(), ss.getSingleDay(), ss.getPrice(), ss.getSector().getTitle(), ss.getRowNum(), ss.getColumnNum());

		return dto;
	}
	
	public static TicketDTO standTicketToDto(StandTicket ss) {

		TicketDTO dto = new TicketDTO(ss.getId(), ss.getDay().getTitle(), ss.getSingleDay(), ss.getPrice(), ss.getSector().getTitle(), -1, -1);

		return dto;
	}
	
	public static List<TicketDTO> convertTicketsToDtos(Set<Ticket> tickets) {

		List<TicketDTO> retVal = new ArrayList<TicketDTO>();

		for (Ticket ticket : tickets) {
			if (ticket instanceof SeatsTicket) {
				retVal.add(TicketDTOConverter.seatsTicketToDto((SeatsTicket) ticket));
			} else {
				retVal.add(TicketDTOConverter.standTicketToDto((StandTicket) ticket));
			}
		}

		return retVal;
	}

}
