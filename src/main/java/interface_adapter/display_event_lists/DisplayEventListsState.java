package interface_adapter.display_event_lists;

import entity.EventList;

import java.util.ArrayList;
import java.util.List;

public class DisplayEventListsState {
    private EventList masterList = null;
    private List<EventList> eventLists = new ArrayList<>();

    public EventList getMasterList() {
        return masterList;
    }
    public List<EventList> getEventLists() {
        return eventLists;
    }
    public void setMasterList(EventList masterList) {
        this.masterList = masterList;
    }
    public void setEventLists(List<EventList> eventLists) {
        this.eventLists = eventLists;
    }
}
