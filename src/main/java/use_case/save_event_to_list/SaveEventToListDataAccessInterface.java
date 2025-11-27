package use_case.save_event_to_list;

import entity.Event;
import entity.EventList;

public interface SaveEventToListDataAccessInterface {
    // to save an event to a list, it may need below data:
    // 1 the event itself
    // 2 the existing eventList

    /**
     * Adds an event to a list.
     * @param event the event to be added.
     * @param eventList the list where we add the event.
     */
    void saveEventToList(Event event, EventList eventList);
}
