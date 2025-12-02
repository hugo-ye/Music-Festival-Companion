package use_case.display_event_list;

public interface DisplayEventListOutputBoundary {
    /**
     * Prepares the success view when an event list is successfully retrieved.
     * The method does the following: updates the view model with the new event list, Fires a property change, and
     * Updates the view manager to switch to the event list screen
     * @param outputData the output data the contains the EventList.
     */
    void prepareSuccessView(DisplayEventListOutputData outputData);

    /**
     * Prepares the failure view when an error occurs while retrieving the event list.
     * @param errorMessage a message that describes why the error occurred.
     */
    void prepareFailView(String errorMessage);
}
