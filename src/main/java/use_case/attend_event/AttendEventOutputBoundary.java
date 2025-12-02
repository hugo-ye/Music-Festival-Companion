package use_case.attend_event;

public interface AttendEventOutputBoundary {
    /**
     * Prepares the success view for the user gallery.
     *
     * @param attendEventOutputData the data to be sent to the Search Results view.
     */
    void prepareSuccessView(AttendEventOutputData attendEventOutputData);

    /**
     * Prepares the failure view for the user gallery.
     *
     * @param error_message the data to be sent to the Search Results view.
     */
    void prepareFailView(String error_message);
}
