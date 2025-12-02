package use_case.save_event_to_list;

import entity.Event;
import entity.EventList;

public class SaveEventToListInputData {
    // to save an event into eventLists, we need to know what the event is and what eventList that the event is saved
    // in

    private final Event event;
    private final EventList[] eventLists;

    public SaveEventToListInputData(Event event, EventList[] eventLists) {
        this.event = event;
        this.eventLists = eventLists;
    }

    // not sure whether i need to use that
    public Event getEvent() {
        return event;
    }

    public EventList[] getEventLists() {
        return eventLists;
    }
}
