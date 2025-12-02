package use_case.create_event_list;

public interface CreateEventListOutputBoundary {
    /**
     * Prepares the success view for the user gallery.
     *
     * @param outputData the data to be sent to the Search Results view.
     */
    void prepareSuccessView(CreateEventListOutputData outputData);

    /**
     * Prepares the success view for the user gallery.
     *
     * @param errorMessage the data to be sent to the Search Results view.
     */
    void prepareFailView(String errorMessage);
}
