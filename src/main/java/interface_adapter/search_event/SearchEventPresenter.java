package interface_adapter.search_event;
import use_case.search_event.SearchEventOutputBoundary;
import use_case.search_event.SearchEventOutputData;


    public class SearchEventPresenter implements SearchEventOutputBoundary {
        private final SearchEventViewModel viewModel;

        public SearchEventPresenter(SearchEventViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        public void present(SearchEventOutputData outputData) {
            //TODO Fill in the present function
            System.out.println(outputData.getEvents());
        }
    }