package interface_adapter.delete_event_list;

import use_case.delete_event_list.DeleteEventListInputBoundary;
import use_case.delete_event_list.DeleteEventListInputData;

public class DeleteEventListController {

    private final DeleteEventListInputBoundary interactor;

    public DeleteEventListController(DeleteEventListInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void delete(String listID) {
        // Called by the UI when user clicks delete button for a given list
        // @param listId the ID of the list to delete (as provided by the view)
        DeleteEventListInputData inputData = new DeleteEventListInputData(listID);

        interactor.execute(inputData);
    }
}