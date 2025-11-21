package use_case.delete_event_list;

public interface DeleteEventListOutputBoundary {
    void prepareSuccessView(DeleteEventListOutputData outputData);
    void prepareFailView(String errorMessage);
}

// Implemented by the presenter in interface adapter