package use_case.attend_event;

import entity.Event;

public class AttendEventOutputData {
    private final String eventName;

    public AttendEventOutputData(Event event) {
        eventName = event.getName();
    }

    public String getEventName() {
        return eventName;
    }
}
