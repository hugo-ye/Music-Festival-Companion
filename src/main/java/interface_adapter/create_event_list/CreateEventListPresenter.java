package interface_adapter.create_event_list;

import java.util.List;

import use_case.create_event_list.CreateEventListOutputBoundary;
import use_case.create_event_list.CreateEventListOutputData;

/**
 * Presenter for the Create Event List use case.
 * Converts the output data from the interactor into an object that the
 * CreateEventListViewModel can use to update the UI.
 */

public class CreateEventListPresenter implements CreateEventListOutputBoundary {

    private final CreateEventListViewModel viewModel;

    public CreateEventListPresenter(CreateEventListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CreateEventListOutputData outputData) {
        final CreateEventListState state = viewModel.getState();

        state.setListId(outputData.getListId());
        state.setListName(outputData.getListName());
        state.setErrorMessage(null);

        final List<EventListSummary> currentLists = state.getLists();
        currentLists.add(new EventListSummary(
                outputData.getListId(),
                outputData.getListName()
        ));
        state.setLists(currentLists);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final CreateEventListState state = viewModel.getState();

        state.setErrorMessage(errorMessage);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
