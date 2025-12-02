package use_case.attend_event;

import entity.Event;

public class AttendEventInputData {
    private final Event event;

    public AttendEventInputData(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
