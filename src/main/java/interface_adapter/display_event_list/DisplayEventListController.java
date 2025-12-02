package interface_adapter.display_event_list;

import use_case.display_event_list.DisplayEventListInputBoundary;
import use_case.display_event_list.DisplayEventListInputData;

/**
 * Controller for the DisplayEventList use case.
 *
 */
public class DisplayEventListController {
    private final DisplayEventListInputBoundary interactor;

    public DisplayEventListController(DisplayEventListInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Display Event List use case.
     * @param listId the ID of the eventList.
     */
    public void execute(String listId) {
        final DisplayEventListInputData inputData = new DisplayEventListInputData(listId);
        interactor.execute(inputData);
    }
}
