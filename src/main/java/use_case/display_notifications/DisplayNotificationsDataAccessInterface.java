package use_case.display_notifications;

import java.util.List;

import entity.Event;

public interface DisplayNotificationsDataAccessInterface {
    /**
     * A method get MasterList of user.
     *
     * @return the MasterList of user.
     */
    List<Event> getMasterListEvents();
}
