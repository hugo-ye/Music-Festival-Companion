package use_case.remove_event_from_list;

import entity.Event;
import entity.EventList;

public interface RemoveEventFromListDataAccessInterface {
    void removeEventFromList(Event removedEvent, EventList targetEventList);
}
