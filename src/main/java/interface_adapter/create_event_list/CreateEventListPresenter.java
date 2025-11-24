package interface_adapter.create_event_list;

import entity.Event;
import entity.EventList;
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

        EventList newList = outputData.getNewList();

        state.setListId(newList.getId());
        state.setListName(newList.getName());
        state.setErrorMessage("");

        List<EventList> updated = new ArrayList<>(state.getLists());

        updated.add(newList);

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