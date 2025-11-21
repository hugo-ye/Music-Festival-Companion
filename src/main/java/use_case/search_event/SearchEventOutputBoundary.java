package use_case.search_event;

public interface SearchEventOutputBoundary {
    void prepareSuccessView(SearchEventOutputData outputData);
    void prepareFailView(String error);
}
