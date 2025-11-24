package use_case.display_event_lists;

import entity.EventList;

import java.util.List;

public interface DisplayEventListsDataAccessInterface {
    public List<EventList> getEventLists();
}
