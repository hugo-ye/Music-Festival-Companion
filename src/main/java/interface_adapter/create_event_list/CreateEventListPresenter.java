package interface_adapter.create_event_list;

import use_case.create_event_list.CreateEventListOutputBoundary;
import use_case.create_event_list.CreateEventListOutputData;

import java.util.ArrayList;
import java.util.List;

public class CreateEventListPresenter implements CreateEventListOutputBoundary {

    private final CreateEventListViewModel viewModel;

    public CreateEventListPresenter(CreateEventListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CreateEventListOutputData outputData) {
        CreateEventListState state = viewModel.getState();

        // update latest created list info
        state.setListId(outputData.getListId());
        state.setListName(outputData.getListName());
        state.setErrorMessage("");

        List<EventListSummary> updated = new ArrayList<>(state.getLists());
        updated.add(new EventListSummary(
                outputData.getListId(),
                outputData.getListName()
        ));
        state.setLists(updated);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        CreateEventListState state = viewModel.getState();

        state.setErrorMessage(errorMessage);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}