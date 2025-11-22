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
        CreateEventListState oldState = viewModel.getState();
        CreateEventListState newState = new CreateEventListState(oldState);

        // update latest created list info
        newState.setListId(outputData.getListId());
        newState.setListName(outputData.getListName());
        newState.setErrorMessage("");

        List<EventListSummary> updated = new ArrayList<>(oldState.getLists());
        updated.add(new EventListSummary(
                outputData.getListId(),
                outputData.getListName()
        ));
        newState.setLists(updated);

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        CreateEventListState oldState = viewModel.getState();
        CreateEventListState newState = new CreateEventListState(oldState);

        newState.setErrorMessage(errorMessage);
        // we keep listName as is, just show error
        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }
}