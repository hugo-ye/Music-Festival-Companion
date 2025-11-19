package interface_adapter.display_event_list;

import entity.EventList;

public class DisplayEventListState {
    private EventList eventList = null;
    public EventList getEventList() {
        return eventList;
    }
    public void setEventList(EventList eventList) {
        this.eventList = eventList;
    }

}
