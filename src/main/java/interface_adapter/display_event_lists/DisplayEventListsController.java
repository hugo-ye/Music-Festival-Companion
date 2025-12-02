package interface_adapter.display_event_lists;

import use_case.display_event_lists.DisplayEventListsInteractor;

public class DisplayEventListsController {
    private final DisplayEventListsInteractor interactor;

    public DisplayEventListsController(DisplayEventListsInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Method to use controller to execute interactor of DisplayEventLists.
     */
    public void execute() {
        interactor.execute();
    }
}
