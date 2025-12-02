package use_case.save_event_to_list;

public interface SaveEventToListOutputBoundary {
    /**
     * Prepares the success view after a successful login attempt.
     * @param outputData the output data
     */
    void present(SaveEventToListOutputData outputData);
}
