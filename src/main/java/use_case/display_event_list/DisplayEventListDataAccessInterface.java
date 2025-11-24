package use_case.display_event_list;

import entity.EventList;

public interface DisplayEventListDataAccessInterface {
    EventList getListById(String listId);
}
