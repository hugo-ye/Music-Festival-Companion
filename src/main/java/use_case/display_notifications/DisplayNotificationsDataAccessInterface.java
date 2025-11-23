package use_case.display_notifications;

import entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface DisplayNotificationsDataAccessInterface {
    List<Event> getNotificationBasedOnData(LocalDate localDate);
}
