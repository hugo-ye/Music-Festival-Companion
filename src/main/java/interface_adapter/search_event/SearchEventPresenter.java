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

    public SearchEventPresenter(SearchEventViewModel viewModel, ViewManagerModel viewManagerModel,
                                DisplaySearchResultsViewModel resultViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.resultViewModel = resultViewModel;
    }

    /**
     * Case of successful search out an event.
     * @param outputData the output date provided.
     */
    public void prepareSuccessView(SearchEventOutputData outputData) {
        System.out.println(outputData.getEvents());

        // viewModel.setState(new SearchEventState());

        final DisplaySearchResultsState displayState = resultViewModel.getState();
        displayState.setEvents(outputData.getEvents());

        resultViewModel.setState(displayState);
        resultViewModel.firePropertyChanged("refresh");

        viewManagerModel.setState(resultViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Case of failing search out an event.
     * @param error description of error situation
     */
    public void prepareFailView(String error) {
        //TODO Fill in the present function
        System.out.println(error);
    }
}
