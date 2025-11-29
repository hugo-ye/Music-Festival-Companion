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

