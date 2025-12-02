package use_case.remove_event_from_list;

public interface RemoveEventFromListOutputBoundary {
    /**
     * Prepares the success view after a successful login attempt.
     * @param outputData the output data containing the username of the logged in user.
     */
    void present(RemoveEventFromListOutputData outputData);
}
