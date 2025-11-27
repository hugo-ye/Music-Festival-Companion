package interface_adapter.save_event_to_list;

import entity.Event;
import entity.EventList;

public class SaveEventToListState {
    private Event event;
    private EventList[] eventLists;
    private String message = "";

    public Event getEvent() { return event; }
    public EventList[] getEventLists() { return eventLists; }
    public String getMessage() { return message; }

    public void setEvent(Event event) { this.event = event; }
    public void setEventLists(EventList[] eventLists) { this.eventLists = eventLists; }
    public void setMessage(String message) { this.message = message; }
}