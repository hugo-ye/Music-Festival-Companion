package use_case.attend_event;

import entity.Event;
import entity.User;

/**
 * DAO interface for the AttendEvent use case.
 */

public interface AttendEventDataAccessInterface {
    // both these methods would be in the InMemoryUserDataAccessObject for better optimization

    /**
     * Returns whether the user is already attending the event.
     * @param event the event to check
     * @return {@code true} if the user already attends the event. {@code false} otherwise.
     */
    boolean alreadyAttends(Event event);

    /**
     * Saves the given event to the masterList.
     * @param event the event to add
     */
    void saveEventToMasterList(Event event);

    /**
     * Method to set current user as user.
     * @param user target user to set.
     */
    void setCurrentUser(User user);
}
