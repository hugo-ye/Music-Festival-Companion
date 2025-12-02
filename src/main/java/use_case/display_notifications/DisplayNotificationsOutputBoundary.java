package use_case.display_notifications;

public interface DisplayNotificationsOutputBoundary {
    /**
     * Prepares the UI when the event is successfully displayed.
     * @param outputData the event details produced by the interactor.
     */
    void prepareSuccessView(DisplayNotificationsOutputData outputData);
}
