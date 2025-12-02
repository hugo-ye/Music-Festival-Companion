package use_case.display_event_lists;

import java.util.List;

import entity.EventList;

public interface DisplayEventListsDataAccessInterface {
    /**
     * A method that get all existing eventLists in user.
     * @return the eventLists of user.
     */
    List<EventList> getEventLists();
}
