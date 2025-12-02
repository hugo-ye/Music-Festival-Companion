package use_case.display_event_list;

import entity.EventList;

public interface DisplayEventListDataAccessInterface {
    // this data access interface seems not been used, should we delete it?
    EventList getListById(String listId);
}
