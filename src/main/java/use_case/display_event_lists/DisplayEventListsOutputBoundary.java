package use_case.display_event_lists;

public interface DisplayEventListsOutputBoundary {
    /**
     * Prepares the UI when the event is successfully displayed.
     * @param outputData the event details produced by the interactor.
     */
    void prepareSuccessView(DisplayEventListsOutputData outputData);

    /**
     * Prepares the UI when the event is successfully displayed.
     * @param errorMessage the event details produced by the interactor.
     */
    void prepareFailView(String errorMessage);
}
