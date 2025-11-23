package interface_adapter.search_event;
import interface_adapter.ViewManagerModel;
import interface_adapter.search_event_result.SearchEventResultState;
import use_case.search_event.SearchEventOutputBoundary;
import use_case.search_event.SearchEventOutputData;


    public class SearchEventPresenter implements SearchEventOutputBoundary {
        private final SearchEventViewModel viewModel;
        private final SearchEventResultState resultViewModel;
        private ViewManagerModel viewManagerModel;

        public SearchEventPresenter(SearchEventViewModel viewModel, ViewManagerModel viewManagerModel, SearchEventResultState resultViewModel) {
            this.viewModel = viewModel;
            this.viewManagerModel = viewManagerModel;
            this.resultViewModel = resultViewModel;
        }

        public void prepareSuccessView(SearchEventOutputData outputData) {
            //TODO Fill in the present function
            System.out.println(outputData.getEvents());
        }

        public void prepareFailView(String error) {
            //TODO Fill in the present function
            System.out.println(error);
        }
    }