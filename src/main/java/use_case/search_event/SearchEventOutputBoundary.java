package use_case.search_event;

public interface SearchEventOutputBoundary {
    /**
     * Prepares the success view after a successful login attempt.
     * @param outputData the output data
     */
    void prepareSuccessView(SearchEventOutputData outputData);

    /**
     * Prepares the failure view after a successful login attempt.
     * @param error the output data
     */
    void prepareFailView(String error);
}
