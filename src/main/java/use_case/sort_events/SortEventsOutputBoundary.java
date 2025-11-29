package use_case.sort_events;

public interface SortEventsOutputBoundary {
    /**
     * Prepares the success view for the user gallery.
     *
     * @param sortEventsOutputData the data to be sent to the Search Results view.
     */
    void prepareSuccessView(SortEventsOutputData sortEventsOutputData);
}
