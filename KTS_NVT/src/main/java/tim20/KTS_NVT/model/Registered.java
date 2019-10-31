package tim20.KTS_NVT.model;

import java.util.Set;

public class Registered extends User {

	private String telephone;

	private Set<Ticket> tickets;

	public Registered() {
	}

	public String getTelephone() {
		return telephone;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

}