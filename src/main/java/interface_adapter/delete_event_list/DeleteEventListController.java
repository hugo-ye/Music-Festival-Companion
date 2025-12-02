package interface_adapter.delete_event_list;

import use_case.delete_event_list.DeleteEventListInputBoundary;
import use_case.delete_event_list.DeleteEventListInputData;

/**
 * Controller for the delete event list use case.
 * This class receives the raw list identifier from the UI,
 * wraps it in a {@link DeleteEventListInputData} object, and passes it
 * to the {@link DeleteEventListInputBoundary} interactor
 */
public class DeleteEventListController {

    private final DeleteEventListInputBoundary interactor;

    /**
     * Constructs a {@code DeleteEventListController} with the given interactor.
     *
     * @param interactor the use case interactor to delegate to
     */
    public DeleteEventListController(DeleteEventListInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the delete event list use case for the given list id.
     *
     * @param listId the id of the list to delete
     */
    public void execute(String listId) {
        final DeleteEventListInputData inputData = new DeleteEventListInputData(listId);
        interactor.execute(inputData);
    }
}
