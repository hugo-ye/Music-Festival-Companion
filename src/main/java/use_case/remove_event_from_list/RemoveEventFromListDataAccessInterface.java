package use_case.remove_event_from_list;

import entity.Event;
import entity.EventList;

public interface RemoveEventFromListDataAccessInterface {
    /**
     * A method that will remove the removedEvent from targetEventList.
     * @param removedEvent target event that user want to remove.
     * @param targetEventList target event that user plan to remove event from.
     */
    void removeEventFromList(Event removedEvent, EventList targetEventList);
}
