package tim20.KTS_NVT.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity()
public class EventDay {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date eventdate;

    @ManyToOne
    private Event event;

    public EventDay() {}

    public EventDay(String title, Date eventdate, Event event) {
        this.title = title;
        this.eventdate = eventdate;
        this.event = event;
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

    public Date getEventDate() {
        return eventdate;
    }

    public void setEventDate(Date datum) {
        this.eventdate = datum;
    }

    @JsonIgnore
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
