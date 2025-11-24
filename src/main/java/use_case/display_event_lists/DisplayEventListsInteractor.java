package use_case.display_event_lists;

import entity.EventList;

import java.util.List;

public class DisplayEventListsInteractor{
    private final DisplayEventListsDataAccessInterface displayEventListsDataAccess;
    private final DisplayEventListsOutputBoundary presenter;

    public DisplayEventListsInteractor(DisplayEventListsDataAccessInterface displayEventListsDataAccess, DisplayEventListsOutputBoundary presenter) {
        this.displayEventListsDataAccess = displayEventListsDataAccess;
        this.presenter = presenter;
    }

    public void execute() {
        List<EventList> eventLists = displayEventListsDataAccess.getEventLists();
        DisplayEventListsOutputData outputData = new DisplayEventListsOutputData(eventLists);
        presenter.prepareSuccessView(outputData);
    }
}
