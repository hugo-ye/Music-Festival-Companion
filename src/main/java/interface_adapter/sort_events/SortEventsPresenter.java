package interface_adapter.sort_events;

import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import use_case.sort_events.SortEventsOutputBoundary;
import use_case.sort_events.SortEventsOutputData;

public class SortEventsPresenter implements SortEventsOutputBoundary {
    public final DisplaySearchResultsViewModel displaySearchResultsViewModel;

    public SortEventsPresenter(DisplaySearchResultsViewModel displaySearchResultsViewModel) {
        this.displaySearchResultsViewModel = displaySearchResultsViewModel;
    }

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

