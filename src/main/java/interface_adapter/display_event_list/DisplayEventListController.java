package interface_adapter.display_event_list;

import use_case.display_event_list.DisplayEventListInteractor;
import use_case.display_event_list.DisplayEventListInputData;

public class DisplayEventListController {
    private final DisplayEventListInteractor interactor;

    public DisplayEventListController(DisplayEventListInteractor interactor) {
        this.interactor = interactor;
    }

    public void execute(String listId) {
        DisplayEventListInputData inputData = new DisplayEventListInputData(listId);
        interactor.execute(inputData);
    }
}