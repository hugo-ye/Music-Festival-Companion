package use_case.display_event;

import entity.Event;

public class DisplayEventInputData {
    private final Event event;

    public DisplayEventInputData(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

}
