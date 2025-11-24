package interface_adapter.display_event_list;

import interface_adapter.ViewManagerModel;
import use_case.display_event_list.DisplayEventListOutputBoundary;
import use_case.display_event_list.DisplayEventListOutputData;

public class DisplayEventListPresenter implements DisplayEventListOutputBoundary {

    private final DisplayEventListViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public DisplayEventListPresenter(DisplayEventListViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(DisplayEventListOutputData outputData) {
        DisplayEventListState state = viewModel.getState();
        state.setEventList(outputData.getEventList());

        viewModel.setState(state);
        viewModel.firePropertyChanged("refresh");

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("Error displaying event list: " + errorMessage);
    }
}