package use_case.display_event_lists;

public interface DisplayEventListsOutputBoundary {
    void prepareSuccessView(DisplayEventListsOutputData outputData);
    void prepareFailView(String errorMessage);
}
