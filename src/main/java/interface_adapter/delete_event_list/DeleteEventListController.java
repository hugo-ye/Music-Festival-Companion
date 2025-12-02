package interface_adapter.delete_event_list;

import use_case.delete_event_list.DeleteEventListInputBoundary;
import use_case.delete_event_list.DeleteEventListInputData;

public class DeleteEventListController {

    private final DeleteEventListInputBoundary interactor;

    public DeleteEventListController(DeleteEventListInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Delete Event List use case using the provided listID.
     * @param listID target eventList id
     */
    public void execute(String listID) {
        final DeleteEventListInputData inputData = new DeleteEventListInputData(listID);
        interactor.execute(inputData);
    }
}
