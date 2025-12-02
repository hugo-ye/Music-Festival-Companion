package use_case.delete_event_list;

public interface DeleteEventListOutputBoundary {
    // Implemented by the presenter in interface adapter
    /**
     * Prepares the success view for the user gallery.
     *
     * @param outputData the data to be sent to the Search Results view.
     */
    void prepareSuccessView(DeleteEventListOutputData outputData);

    /**
     * Prepares the failure view for the user gallery.
     *
     * @param errorMessage the data to be sent to the Search Results view.
     */
    void prepareFailView(String errorMessage);
}
