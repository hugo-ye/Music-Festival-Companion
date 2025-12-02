package use_case.display_event;

public interface DisplayEventOutputBoundary {
    /**
     * Prepares the UI when the event is successfully displayed.
     * @param outputData the event details produced by the interactor.
     */
    void prepareSuccessView(DisplayEventOutputData outputData);
}
