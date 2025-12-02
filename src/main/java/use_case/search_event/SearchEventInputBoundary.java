package use_case.search_event;

public interface SearchEventInputBoundary {
    /**
     * Retrieves event data from the DAO, parse it into an Event object.
     *
     * @param inputData the input data containing the search criteria.
     */
    void execute(SearchEventInputData inputData);
}
