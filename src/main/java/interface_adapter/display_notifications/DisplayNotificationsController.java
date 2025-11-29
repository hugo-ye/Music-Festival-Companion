package interface_adapter.display_notifications;

import use_case.display_notifications.DisplayNotificationsInputBoundary;
import use_case.display_notifications.DisplayNotificationsInputData;

import java.time.LocalDate;

public class DisplayNotificationsController {
    private final DisplayNotificationsInputBoundary interactor;

    public DisplayNotificationsController(DisplayNotificationsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(LocalDate localDate){
        DisplayNotificationsInputData inputData = new DisplayNotificationsInputData(localDate);
        interactor.execute(inputData);
    }
}
