package interface_adapter.sort_events;

import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import use_case.sort_events.SortEventsOutputBoundary;
import use_case.sort_events.SortEventsOutputData;

/**
 * The Presenter for the Sort Events Use Case.
 */
public class SortEventsPresenter implements SortEventsOutputBoundary {
    private final DisplaySearchResultsViewModel displaySearchResultsViewModel;

    public SortEventsPresenter(DisplaySearchResultsViewModel displaySearchResultsViewModel) {
        this.displaySearchResultsViewModel = displaySearchResultsViewModel;
    }

    /**
     * Prepares the success view after events have been sorted.
     * This method updates the {@link DisplaySearchResultsViewModel} with the sorted list of events the criteria
     * used to sort the events and the sorting order.
     * @param sortEventsOutputData the output data that contains the list of events, the criteria, and the order.
     */
    @Override
    public void prepareSuccessView(SortEventsOutputData sortEventsOutputData) {
        final DisplaySearchResultsState state = displaySearchResultsViewModel.getState();
        state.setEvents(sortEventsOutputData.getEvents());
        state.setSortEventsCriteria(sortEventsOutputData.getSortEventsCriteria());
        state.setSortEventsOrder(sortEventsOutputData.getSortEventsOrder());
        displaySearchResultsViewModel.setState(state);
        displaySearchResultsViewModel.firePropertyChanged("refresh");

    }
}

