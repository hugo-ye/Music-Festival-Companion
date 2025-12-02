package use_case.display_event_lists;

import java.util.List;

import entity.EventList;

/**
 * The {@code DisplayEventListsInteractor} Retrieves all event lists and passes them to the presenter for display.
 */
public class DisplayEventListsInteractor {
    private final DisplayEventListsDataAccessInterface displayEventListsDataAccess;
    private final DisplayEventListsOutputBoundary presenter;

    public DisplayEventListsInteractor(DisplayEventListsDataAccessInterface displayEventListsDataAccess,
                                       DisplayEventListsOutputBoundary presenter) {
        this.displayEventListsDataAccess = displayEventListsDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the use case by retrieving all event lists and sends them to the presenter.
     */
    public void execute() {
        final List<EventList> eventLists = displayEventListsDataAccess.getEventLists();
        final DisplayEventListsOutputData outputData = new DisplayEventListsOutputData(eventLists);
        presenter.prepareSuccessView(outputData);
    }
}
