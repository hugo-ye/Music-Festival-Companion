package use_case.save_event_to_list;

import entity.Event;
import entity.EventList;

public interface SaveEventToListDataAccessInterface {
    // to save an event to a list, it may need below data:
    // 1 the event itself
    // 2 the existing eventList

    void saveEventToList(Event event, EventList eventList);
}
