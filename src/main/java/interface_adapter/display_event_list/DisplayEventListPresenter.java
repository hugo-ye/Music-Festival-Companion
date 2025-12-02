package interface_adapter.display_event_list;

import interface_adapter.ViewManagerModel;
import use_case.display_event_list.DisplayEventListOutputBoundary;
import use_case.display_event_list.DisplayEventListOutputData;

/**
 * Presenter for the DisplayEventList use case.
 */
public class DisplayEventListPresenter implements DisplayEventListOutputBoundary {

    private final DisplayEventListViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public DisplayEventListPresenter(DisplayEventListViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the success view when an event list is successfully retrieved.
     * The method does the following: updates the view model with the new event list, Fires a property change, and
     * Updates the view manager to switch to the event list screen
     * @param outputData the output data the contains the EventList.
     */
    @Override
    public void prepareSuccessView(DisplayEventListOutputData outputData) {
        final DisplayEventListState state = viewModel.getState();
        state.setEventList(outputData.getEventList());

        viewModel.setState(state);
        viewModel.firePropertyChanged("refresh");

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view when an error occurs while retrieving the event list.
     * @param errorMessage a message that describes why the error occurred.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        System.err.println("Error displaying event list: " + errorMessage);
    }
}
