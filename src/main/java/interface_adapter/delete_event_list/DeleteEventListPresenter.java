package interface_adapter.delete_event_list;

import java.util.ArrayList;
import java.util.List;

import interface_adapter.create_event_list.CreateEventListState;
import interface_adapter.create_event_list.CreateEventListViewModel;
import interface_adapter.create_event_list.EventListSummary;
import use_case.delete_event_list.DeleteEventListOutputBoundary;
import use_case.delete_event_list.DeleteEventListOutputData;

/**
 * Presenter for the Delete Event List use case.
 * Updates both the delete-event-list view model and the create-event-list
 * view model so the UI stays consistent after a deletion.
 */
public class DeleteEventListPresenter implements DeleteEventListOutputBoundary {

    private final DeleteEventListViewModel deleteViewModel;
    private final CreateEventListViewModel createViewModel;

    /**
     * Constructs a presenter for the delete event list use case.
     *
     * @param deleteViewModel the view model holding delete-event-list state
     * @param createViewModel the view model holding create-event-list state
     */
    public DeleteEventListPresenter(DeleteEventListViewModel deleteViewModel,
                                    CreateEventListViewModel createViewModel) {
        this.deleteViewModel = deleteViewModel;
        this.createViewModel = createViewModel;
    }

    /**
     * Updates the view models to reflect a successful deletion.
     *
     * @param outputData the output data containing the ID of the deleted list
     */
    @Override
    public void prepareSuccessView(DeleteEventListOutputData outputData) {
        final String deletedId = outputData.getListId();

        // Update delete view model
        final DeleteEventListState deleteState = deleteViewModel.getState();
        deleteState.setListId(deletedId);
        deleteState.setErrorMessage("");
        deleteViewModel.setState(deleteState);
        deleteViewModel.firePropertyChanged();

        // Update the create-list viewmodel so AllEventListsView refreshes rows
        final CreateEventListState createState = createViewModel.getState();

        final List<EventListSummary> oldLists = createState.getLists();
        final List<EventListSummary> filtered = new ArrayList<>();

        for (EventListSummary summary : oldLists) {
            if (!summary.getId().equals(deletedId)) {
                filtered.add(summary);
            }
        }
        createState.setLists(filtered);
        createState.setErrorMessage("");

        createViewModel.setState(createState);
        createViewModel.firePropertyChanged();
    }

    /**
     * Updates the delete view model when deletion fails.
     *
     * @param errorMessage the error message to display
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final DeleteEventListState deleteState = deleteViewModel.getState();
        deleteState.setErrorMessage(errorMessage);

        deleteViewModel.setState(deleteState);
        deleteViewModel.firePropertyChanged();
    }
}
