package use_case.create_event_list;

/**
 * Output boundary for the Create Event List use case.
 */
public interface CreateEventListOutputBoundary {

    /**
     * Prepares the view to display a successful view for creating an event list.
     *
     * @param outputData the output data containing the ID and name of the newly created event list
     */
    void prepareSuccessView(CreateEventListOutputData outputData);

    /**
     * Prepares the view to display an error message when event list creation fails.
     *
     * @param errorMessage a user readable explanation of why the creation failed
     */
    void prepareFailView(String errorMessage);
}
