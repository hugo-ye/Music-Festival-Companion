package use_case.display_event;

import java.util.List;

import entity.EventList;

public interface DisplayEventDataAccessInterface {
    /**
     * A method that get all existing eventLists in user.
     * @return the eventLists of user.
     */
    List<EventList> getEventLists();
}
