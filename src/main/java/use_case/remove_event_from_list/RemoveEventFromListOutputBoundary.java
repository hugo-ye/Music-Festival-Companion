package use_case.remove_event_from_list;

public interface RemoveEventFromListOutputBoundary {
    /**
     * Prepares the success view after a successful attempt.
     * @param outputData the output data
     */
    void present(RemoveEventFromListOutputData outputData);
}
