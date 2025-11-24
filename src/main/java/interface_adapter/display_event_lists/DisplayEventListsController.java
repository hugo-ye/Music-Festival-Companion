package interface_adapter.display_event_lists;

import use_case.display_event_lists.DisplayEventListsInteractor;

public class DisplayEventListsController {
    private final DisplayEventListsInteractor interactor;

    public DisplayEventListsController(DisplayEventListsInteractor interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}
