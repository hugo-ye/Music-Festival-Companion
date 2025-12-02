package use_case.display_notifications;

public interface DisplayNotificationsInputBoundary {
    /**
     * Executes the display_notifications use case.
     * @param inputData contains the date.
     */
    void execute(DisplayNotificationsInputData inputData);
}
