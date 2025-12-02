package use_case.display_event_list;

import entity.EventList;

public class DisplayEventListOutputData {
    private final EventList eventList;

    public DisplayEventListOutputData(EventList eventList) {
        this.eventList = eventList;
    }

    public EventList getEventList() {
        return eventList;
    }
}
