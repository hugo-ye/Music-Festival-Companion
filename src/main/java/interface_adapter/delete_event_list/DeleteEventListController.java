package interface_adapter.delete_event_list;

import use_case.delete_event_list.DeleteEventListInputBoundary;
import use_case.delete_event_list.DeleteEventListInputData;

public class DeleteEventListController {

    private final DeleteEventListInputBoundary interactor;

    public DeleteEventListController(DeleteEventListInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void delete(String rawListId) {
        // 1. Convert raw input (trim whitespace)
        String listId = rawListId.trim();

        // 2. Create Input Data DTO
        DeleteEventListInputData inputData = new DeleteEventListInputData(listId);

        // 3. Call interactor (use case)
        interactor.execute(inputData);
    }
}
