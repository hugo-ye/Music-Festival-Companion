package interface_adapter.search_event;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import use_case.search_event.SearchEventOutputBoundary;
import use_case.search_event.SearchEventOutputData;

/**
 * Presenter for the SearchEvent use case.
 */
public class SearchEventPresenter implements SearchEventOutputBoundary {

    private final SearchEventViewModel viewModel;
    private final DisplaySearchResultsViewModel resultViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchEventPresenter(final SearchEventViewModel viewModel, final ViewManagerModel viewManagerModel,
                                final DisplaySearchResultsViewModel resultViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.resultViewModel = resultViewModel;
    }

    @Override
    public void prepareSuccessView(final SearchEventOutputData outputData) {
        System.out.println(outputData.getEvents());

        final DisplaySearchResultsState displayState = resultViewModel.getState();
        displayState.setEvents(outputData.getEvents());

        resultViewModel.setState(displayState);
        resultViewModel.firePropertyChanged("refresh");

        viewManagerModel.setState(resultViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(final String error) {
        final SearchEventState state = viewModel.getState();
        state.setErrorMessage(error);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
