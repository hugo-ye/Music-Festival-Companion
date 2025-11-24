package use_case.remove_event_from_list;

import entity.Event;
import entity.EventList;

public class RemoveEventFromListOutputData {
    private final String message;
    private final String targetListId; // NEW FIELD

    public RemoveEventFromListOutputData(String message, String targetListId) { // UPDATED CONSTRUCTOR
        this.message = message;
        this.targetListId = targetListId;
    }

    public String getMessage() {
        return message;
    }

    public String getTargetListId() {
        return targetListId; // NEW GETTER
    }
}