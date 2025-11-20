package use_case.attend_event;

import entity.Event;
import entity.EventList;
import entity.User;

public interface AttendEventDataAccessInterface {
    // the event is already in the master_list
    boolean alreadyAttends(User user, Event event);

    User getByUsername(String username);

    void saveAttend(User user, Event event);
}
