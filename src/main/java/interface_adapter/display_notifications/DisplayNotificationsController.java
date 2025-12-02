package interface_adapter.display_notifications;

import java.time.LocalDate;

import use_case.display_notifications.DisplayNotificationsInputBoundary;
import use_case.display_notifications.DisplayNotificationsInputData;

public class DisplayNotificationsController {
    private final DisplayNotificationsInputBoundary interactor;

    public DisplayNotificationsController(DisplayNotificationsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Method to use controller to execute interactor of DisplayNotifications.
     * @param localDate date of given.
     */
    public void execute(LocalDate localDate) {
        final DisplayNotificationsInputData inputData = new DisplayNotificationsInputData(localDate);
        interactor.execute(inputData);
    }
}
