package use_case.display_notifications;

import entity.Event;

import java.util.List;

public interface DisplayNotificationsDataAccessInterface {
    List<Event> getMasterListEvents();
}
