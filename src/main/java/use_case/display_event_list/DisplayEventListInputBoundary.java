package use_case.display_event_list;

public interface DisplayEventListInputBoundary {
    /**
     * Executes the Display Event List use case using the provided input data.
     * This method performs the following steps: Retrieves the current user, checks whether the user is logged in,
     * Retrieves the Eventlist given an id, if no such list exists then checks if the list is the masterlist,
     * Returns a success view with the event list if found, or a failure view otherwise.
     * @param inputData the input containing the ID of the event list to display
     */
    void execute(DisplayEventListInputData inputData);
}
