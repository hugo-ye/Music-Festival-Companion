package use_case.remove_event_from_list;

import entity.Event;
import entity.EventList;

public class RemoveEventFromListInputData {
    private final Event removedEvent;
    private final EventList targetEventList;

    public RemoveEventFromListInputData(Event removedEvent, EventList targetEventList) {
        this.removedEvent = removedEvent;
        this.targetEventList = targetEventList;
    }

    public Event getRemovedEvent() {
        return removedEvent;
    }

    public EventList getTargetEventList() {
        return targetEventList;
    }
}
