package interface_adapter.search_event;
import interface_adapter.ViewManagerModel;
import interface_adapter.display_search_results.DisplaySearchResultsState;
import interface_adapter.display_search_results.DisplaySearchResultsViewModel;
import use_case.search_event.SearchEventOutputBoundary;
import use_case.search_event.SearchEventOutputData;


    public class SearchEventPresenter implements SearchEventOutputBoundary {
        private final SearchEventViewModel viewModel;
        private final DisplaySearchResultsViewModel resultViewModel;
        private ViewManagerModel viewManagerModel;

        public SearchEventPresenter(SearchEventViewModel viewModel, ViewManagerModel viewManagerModel, DisplaySearchResultsViewModel resultViewModel) {
            this.viewModel = viewModel;
            this.viewManagerModel = viewManagerModel;
            this.resultViewModel = resultViewModel;
        }

        public void prepareSuccessView(SearchEventOutputData outputData) {
            //TODO Fill in the present function
            System.out.println(outputData.getEvents());
            viewModel.setState(new SearchEventState());
            DisplaySearchResultsState displayState = resultViewModel.getState();
            displayState.setEvents(outputData.getEvents());
            System.out.println("search result view name is: " + resultViewModel.getViewName());
            resultViewModel.setState(displayState);
            resultViewModel.firePropertyChanged();

            viewManagerModel.setState(resultViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        }

        public void prepareFailView(String error) {
            //TODO Fill in the present function
            System.out.println(error);
        }
    }