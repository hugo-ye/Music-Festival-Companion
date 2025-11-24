package use_case.display_event_list;

public interface DisplayEventListOutputBoundary {
    void prepareSuccessView(DisplayEventListOutputData outputData);
    void prepareFailView(String errorMessage);
}