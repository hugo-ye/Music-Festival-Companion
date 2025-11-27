package interface_adapter.sort_events;

import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import use_case.sort_events.SortEventsOutputBoundary;
import use_case.sort_events.SortEventsOutputData;

/**
 * Presenter class for the sort events use case
 * <p>
 *     This class implements {@link SortEventsOutputBoundary} and is responsible for receiving the sorted events from
 *     the interactor
 * </p>
 */
public class SortEventsPresenter implements SortEventsOutputBoundary {
    public final DisplaySearchResultsViewModel displaySearchResultsViewModel;

    public SortEventsPresenter(DisplaySearchResultsViewModel displaySearchResultsViewModel) {
        this.displaySearchResultsViewModel = displaySearchResultsViewModel;
    }

    /**
     * Prepares the success view after events have been sorted.
     * <p>
     *     This method updates the {@link DisplaySearchResultsViewModel} with the sorted list of events the criteria
     *     used to sort the events and the sorting order.
     * </p>
     * @param outputData the output data that contains the list of events, the criteria, and the order.
     */
    @Override
    public void prepareSuccessView(SortEventsOutputData outputData) {
        DisplaySearchResultsState state = displaySearchResultsViewModel.getState();
        state.setEvents(outputData.getEvents());
        state.setSortEventsCriteria(outputData.getSortEventsCriteria());
        state.setSortEventsOrder(outputData.getSortEventsOrder());
        displaySearchResultsViewModel.setState(state);
        displaySearchResultsViewModel.firePropertyChanged("refresh");

    }
}

