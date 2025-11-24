package use_case.attend_event;

import entity.Event;

public interface AttendEventDataAccessInterface {
    // both these methods would be in the InMemoryUserDataAccessObject for better optimization
    boolean alreadyAttends(Event event);
    void saveEventToMasterList(Event event);
}
