package interface_adapter.delete_event_list;

import interface_adapter.create_event_list.CreateEventListState;
import interface_adapter.create_event_list.CreateEventListViewModel;
import interface_adapter.create_event_list.EventListSummary;
import use_case.delete_event_list.DeleteEventListOutputBoundary;
import use_case.delete_event_list.DeleteEventListOutputData;

import java.util.ArrayList;
import java.util.List;

public class DeleteEventListPresenter implements DeleteEventListOutputBoundary {

    private final DeleteEventListViewModel deleteViewModel;
    private final CreateEventListViewModel createViewModel;

    public DeleteEventListPresenter(DeleteEventListViewModel deleteViewModel,
                                    CreateEventListViewModel createViewModel) {
        this.deleteViewModel = deleteViewModel;
        this.createViewModel = createViewModel;
    }

    @Override
    public void prepareSuccessView(DeleteEventListOutputData outputData) {
        String deletedId = outputData.getListId();

        // Update the delete viewmodel
        DeleteEventListState deleteState = deleteViewModel.getState();
        deleteState.setListId(deletedId);
        deleteState.setErrorMessage("");
        deleteViewModel.setState(deleteState);
        deleteViewModel.firePropertyChanged();

        // Update the create-list viewmodel so AllEventListsView refreshes rows
        CreateEventListState createState = createViewModel.getState();

        List<EventListSummary> oldLists = createState.getLists();
        List<EventListSummary> filtered = new ArrayList<>();

        for (EventListSummary summary : oldLists) {
            if (!summary.getId().equals(deletedId)) {
                filtered.add(summary);
            }
        }
        createState.setLists(filtered);
        createState.setErrorMessage("");   // clear errors on success

        createViewModel.setState(createState);
        createViewModel.firePropertyChanged(); // triggers AllEventListsView.propertyChange
    }
    @Override
    public void prepareFailView(String errorMessage) {
        DeleteEventListState deleteState = deleteViewModel.getState();
        deleteState.setErrorMessage(errorMessage);

        deleteViewModel.setState(deleteState);
        deleteViewModel.firePropertyChanged();
    }
}