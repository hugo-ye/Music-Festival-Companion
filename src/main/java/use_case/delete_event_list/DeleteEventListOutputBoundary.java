package use_case.delete_event_list;

/**
 * Output boundary for the Delete Event List use case.
 * The presenter implements this interface to format the response model
 * for the UI layer.
 */
public interface DeleteEventListOutputBoundary {

    /**
     * Prepares the view to display a successful deletion.
     *
     * @param outputData the response model containing details about the deleted list
     */
    void prepareSuccessView(DeleteEventListOutputData outputData);

    /**
     * Prepares the view to display an error message when deletion fails.
     *
     * @param errorMessage a user readble description of why the deletion operation failed
     */
    void prepareFailView(String errorMessage);
}
